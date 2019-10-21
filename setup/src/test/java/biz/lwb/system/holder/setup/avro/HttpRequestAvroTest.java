package biz.lwb.system.holder.setup.avro;


import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class HttpRequestAvroTest {
    @Test
    public void serialize() throws IOException {
        HttpRequestAvro requestAvro = HttpRequestAvro.newBuilder()
                .setFirst("firstname")
                .setSecond("lastname")
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
}