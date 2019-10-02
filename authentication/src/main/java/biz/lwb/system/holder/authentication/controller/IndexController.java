package biz.lwb.system.holder.authentication.controller;

import biz.lwb.system.holder.authentication.LwbholderClient;
import biz.lwb.system.holder.authentication.model.IndexResult;
import biz.lwb.system.holder.inmemory.service.db.api.FiveServiceSpringData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private ControllerProperties controllerProperties;

    @Autowired
    private LwbholderClient lwbholderClient;

    @GetMapping("auth")
    public IndexResult getdiscovery() {
        IndexResult indexResult = new IndexResult();
        indexResult.setName(controllerProperties.getName());
        indexResult.setValue("indexvalue");
        return indexResult;
    }

    @GetMapping("client")
    public List<FiveServiceSpringData> getClient(){
        return lwbholderClient.helloWorld();
    }

}
