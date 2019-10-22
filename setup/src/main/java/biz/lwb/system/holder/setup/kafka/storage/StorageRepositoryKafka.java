package biz.lwb.system.holder.setup.kafka.storage;

import biz.lwb.system.holder.setup.avro.HttpRequestEvent;
import biz.lwb.system.holder.setup.kafka.HttpRequestEventKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;

public class StorageRepositoryKafka implements StorageRepository {

    @Autowired
    private HttpRequestEventKafkaProducer httpRequestEventKafkaProducer;

    @Override
    public HttpRequestEvent save(HttpRequestEvent event) {
        return StorageRepository.super.save(event);
    }
}
