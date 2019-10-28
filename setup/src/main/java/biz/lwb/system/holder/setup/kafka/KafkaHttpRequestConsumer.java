package biz.lwb.system.holder.setup.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaHttpRequestConsumer {

    @KafkaListener(topics = "http-request-events-1", groupId = "group_id", containerFactory = "httpRequestEventKafkaContainerFactory")
    public void consume(GenericRecord message) {
        log.info("KafkaHttpRequestConsumer: {}" + message);
    }

}
