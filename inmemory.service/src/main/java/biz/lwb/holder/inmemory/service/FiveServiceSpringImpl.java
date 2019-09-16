package biz.lwb.holder.inmemory.service;

import biz.lwb.holder.inmemory.service.api.FiveServiceSpring;
import biz.lwb.holder.inmemory.service.api.FiveServiceSpringData;

import java.util.Collections;
import java.util.List;

public class FiveServiceSpringImpl implements FiveServiceSpring {

    @Override
    public FiveServiceSpringData findById(Integer id) {
        FiveServiceSpringData data = new FiveServiceSpringData();
        data.setBefore("five");
        data.setAfter("five " + id);
        return data;
    }

    @Override
    public List<FiveServiceSpringData> findAll() {
        return Collections.emptyList();
    }
}
