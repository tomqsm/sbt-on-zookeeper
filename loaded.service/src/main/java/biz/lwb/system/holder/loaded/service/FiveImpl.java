package biz.lwb.system.holder.loaded.service;

import biz.lwb.system.holder.loaded.service.api.FiveResult;
import biz.lwb.system.holder.loaded.service.api.FiveService;

public class FiveImpl implements FiveService {
    @Override
    public FiveResult five(String s) {
        FiveResult fiveResult = new FiveResult();
        fiveResult.setBefore(s);
        fiveResult.setAfter(s + " 5");
        return fiveResult;
    }
}
