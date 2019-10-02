package biz.lwb.system.holder.authentication;

import biz.lwb.system.holder.inmemory.service.db.api.FiveServiceSpringData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "lwbholder.setup")
public interface LwbholderSetupClient {

    @RequestMapping(path = "/service.spring.injection", method = RequestMethod.GET)
    @ResponseBody
    List<FiveServiceSpringData> doSetup();


}
