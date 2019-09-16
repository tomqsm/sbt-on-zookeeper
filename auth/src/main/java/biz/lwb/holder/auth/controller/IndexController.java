package biz.lwb.holder.auth.controller;

import biz.lwb.holder.auth.SpringappClient;
import biz.lwb.holder.auth.model.IndexResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RibbonClient(name = "hello-service", configuration = HelloServiceConfiguration.class)
public class IndexController {

    @Autowired
    private ControllerProperties controllerProperties;

    @Autowired
    private SpringappClient springappClient;

    @GetMapping("auth")
    public IndexResult getdiscovery() {
        IndexResult indexResult = new IndexResult();
        indexResult.setName(controllerProperties.getName());
        indexResult.setValue("indexvalue");
        return indexResult;
    }

    @GetMapping("client")
    public String getClient(){
        return springappClient.helloWorld();
    }

}
