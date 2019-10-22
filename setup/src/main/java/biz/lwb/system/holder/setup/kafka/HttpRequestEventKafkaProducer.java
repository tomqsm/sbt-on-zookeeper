package biz.lwb.system.holder.setup.kafka;

import biz.lwb.system.holder.setup.avro.HttpRequestAvro;
import biz.lwb.system.holder.setup.avro.HttpRequestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class HttpRequestEventKafkaProducer {

    public static final String TOPIC = "http-request-events";

    private final KafkaTemplate<String, HttpRequestEvent> httpRequestEventsKafkaTemplate;

    public HttpRequestEventKafkaProducer(KafkaTemplate<String, HttpRequestEvent> httpRequestEventsKafkaTemplate){
        this.httpRequestEventsKafkaTemplate = httpRequestEventsKafkaTemplate;
    }

    public void sendMessage(HttpRequestEvent message) {
        ListenableFuture<SendResult<String, HttpRequestEvent>> send = httpRequestEventsKafkaTemplate.send(TOPIC, message.getCorrelationId(), message);
        send.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("sending log failed: ", throwable);
            }

            @Override
            public void onSuccess(SendResult<String, HttpRequestEvent> stringStringSendResult) {
                log.info("producing message: {}", message);
            }
        });
    }
}
