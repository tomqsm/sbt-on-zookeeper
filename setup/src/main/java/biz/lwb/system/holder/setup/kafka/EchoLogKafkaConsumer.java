package biz.lwb.system.holder.setup.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EchoLogKafkaConsumer {

    @KafkaListener(topics = "log-error", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")
    public void consume(String message) {
        System.out.println("XX" + message);
    }

}
