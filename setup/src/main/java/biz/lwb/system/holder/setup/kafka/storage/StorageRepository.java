package biz.lwb.system.holder.setup.kafka.storage;

import biz.lwb.system.holder.setup.avro.HttpRequestEvent;

public interface StorageRepository {

    default HttpRequestEvent save (HttpRequestEvent event){

        return event;
    }

}
