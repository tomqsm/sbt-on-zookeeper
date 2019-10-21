package biz.lwb.system.holder.setup.kafka;

import biz.lwb.system.holder.setup.avro.HttpRequestAvro;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaHttpRequestConsumer {

    @KafkaListener(topics = "http-requests", groupId = "group_id", containerFactory = "htppRequestKafkaContainerFactory")
    public void consume(GenericRecord message) {
        System.out.println("KafkaHttpRequestConsumer: " + message);
    }

}
