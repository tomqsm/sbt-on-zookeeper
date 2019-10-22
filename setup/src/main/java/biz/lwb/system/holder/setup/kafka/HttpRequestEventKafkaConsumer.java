package biz.lwb.system.holder.setup.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HttpRequestEventKafkaConsumer {

    @KafkaListener(topics = "http-request-events", groupId = "group_id", containerFactory = "httpRequestEventKafkaContainerFactory")
    public void consume(GenericRecord message) {
        System.out.println("KafkaHttpRequestConsumer: " + message);
    }

}
