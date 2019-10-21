package biz.lwb.system.holder.setup.kafka;

import biz.lwb.system.holder.setup.avro.HttpRequestAvro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class KafkaHttpRequestProducer {

    public static final String TOPIC = "http-requests";

    private final KafkaTemplate<String, HttpRequestAvro> kafkaHttpRequestTemplate;

    public KafkaHttpRequestProducer(KafkaTemplate<String, HttpRequestAvro> kafkaHttpRequestTemplate){
        this.kafkaHttpRequestTemplate = kafkaHttpRequestTemplate;
    }

    public void sendMessage(HttpRequestAvro message) {
        ListenableFuture<SendResult<String, HttpRequestAvro>> send = kafkaHttpRequestTemplate.send(TOPIC, message);
        send.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("sending log failed: ", throwable);
            }

            @Override
            public void onSuccess(SendResult<String, HttpRequestAvro> stringStringSendResult) {
                log.info("producing message: {}", message);
            }
        });
    }
}
