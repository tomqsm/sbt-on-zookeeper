package biz.lwb.system.holder.setup.avro;


import lombok.extern.slf4j.Slf4j;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Slf4j
public class HttpRequestAvroTest {
    @Test
    public void serialize() throws IOException {
        HttpRequestAvro requestAvro = HttpRequestAvro.newBuilder()
                .setFirst("firstname")
                .setSecond("lastname")
                .build();


        Car as = Car.newBuilder()
                .setPlateNumber("as")
                .build();

        DatumWriter<HttpRequestAvro> writer = new SpecificDatumWriter<>(HttpRequestAvro.class);
        DataFileWriter<HttpRequestAvro> dataFileWriter = new DataFileWriter<>(writer);
        File file = new File("httpRequest.avro");
        dataFileWriter.create(requestAvro.getSchema(), file);
        dataFileWriter.close();

    }

    @Test
    public void deserialize() throws IOException {
        File file = new File("httpRequest.avro");
        DatumReader<HttpRequestAvro> userDatumReader = new SpecificDatumReader<>(HttpRequestAvro.class);
        DataFileReader<HttpRequestAvro> dataFileReader = new DataFileReader<>(file, userDatumReader);
        assertThat(dataFileReader.hasNext()).isTrue();
    }

    @Test
    public void testAvroDsl(){
        String corr = UUID.randomUUID().toString();
        HttpRequestEvent httpRequestEvent = HttpRequestEvent.newBuilder()
                .setCorrelationId(corr)
                .setHttpMethod(HttpMethod.GET)
                .setTimestamp(LocalTime.now())
                .setUri("/localhost")
                .setHeaders(Map.of("X_CORRELATION", List.of(corr)))
                .build();
        log.info("created: {}", httpRequestEvent);
    }
}