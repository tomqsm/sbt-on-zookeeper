package biz.lwb.system.holder.setup.avro;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class HttpRequestAvroTest {
    @Test
    public void testAvroDsl(){
        String corr = UUID.randomUUID().toString();
        HttpRequestEvent httpRequestEvent = HttpRequestEvent.newBuilder()
                .setCorrelationId(corr)
                .setHttpMethod(HttpMethod.GET)
                .setTimestamp(System.currentTimeMillis())
                .setUri("/localhost")
                .setHeaders(Map.of("X_CORRELATION", List.of(corr)))
                .build();
        log.info("created: {}", httpRequestEvent);
    }
}