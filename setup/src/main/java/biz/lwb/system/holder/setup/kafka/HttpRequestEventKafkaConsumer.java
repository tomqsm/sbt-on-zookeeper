package biz.lwb.system.holder.setup.kafka;

import biz.lwb.system.holder.setup.avro.HttpRequestEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HttpRequestEventKafkaConsumer {

    @KafkaListener(topics = "http-request-events", groupId = "group_id_2", containerFactory = "httpRequestEventKafkaContainerFactory")
    public void consume(GenericRecord message) {
        HttpRequestEvent requestEvent = (HttpRequestEvent) SpecificData.get().deepCopy(HttpRequestEvent.SCHEMA$, message);
        System.out.println("HttpRequestEventKafkaConsumer: " + requestEvent.getCorrelationId());
        log.info("correlationId: {}", requestEvent.getCorrelationId());
    }

}
