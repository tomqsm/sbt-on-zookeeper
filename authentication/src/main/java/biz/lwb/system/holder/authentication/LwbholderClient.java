package biz.lwb.system.holder.authentication;

import biz.lwb.system.holder.inmemory.service.db.api.FiveServiceSpringData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@EnableFeignClients
@EnableDiscoveryClient
public class LwbholderClient {

    @Autowired
    private LwbholderSetupClient setupClient;

    public List<FiveServiceSpringData> helloWorld() {
        return setupClient.doSetup();
    }
}
