package biz.lwb.system.holder.setup.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class Consumer {

    @KafkaListener(topics = Producer.TOPIC, groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")
    public void consume(String message) throws IOException {
        log.info(String.format("#### -> Consumed message -> %s", message));
    }

}
