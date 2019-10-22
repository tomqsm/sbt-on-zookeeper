package biz.lwb.system.holder.setup.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class KafkaHttpRequestPollingConsumer {

    @Autowired
    private ConcurrentKafkaListenerContainerFactory<String, GenericRecord> htppRequestKafkaContainerFactory;

    @Async("asyncExecutor")
    public CompletableFuture<? extends ConsumerRecords<? super String, ? super GenericRecord>> consume() {

        Consumer<? super String, ? super GenericRecord> consumer = htppRequestKafkaContainerFactory.getConsumerFactory().createConsumer();
        consumer.subscribe(List.of("http-requests"));

        return CompletableFuture.supplyAsync(() -> consumer
                .poll(Duration.ofMillis(100)));

    }
}
