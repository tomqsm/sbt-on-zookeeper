package biz.lwb.system.holder.setup.controller;

import biz.lwb.system.holder.inmemory.service.db.api.FiveServiceSpring;
import biz.lwb.system.holder.inmemory.service.db.api.FiveServiceSpringData;
import biz.lwb.system.holder.loaded.service.api.FiveResult;
import biz.lwb.system.holder.loaded.service.api.FiveService;
import biz.lwb.system.holder.setup.properties.LadProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class IndexController {

    @Autowired
    private FiveServiceSpring repository;

    @Autowired
    private Environment environment;

    @Autowired
    private LadProperties cloudProperties;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ZookeeperServiceRegistry serviceRegistry;

//    @Autowired
//    private ZookeeperServiceRegistry serviceRegistry;
    //@Autowired
    //private CuratorFramework curatorFramework;

    @GetMapping("service")
    public List<FiveResult> getServiceInfo() {
        ServiceLoader<FiveService> load = ServiceLoader.load(FiveService.class);
        return load.stream()
                .map(capitalizerDryProvider -> capitalizerDryProvider.get())
                .filter(Objects::nonNull)
                .map(service -> service.five("rumba"))
                .collect(Collectors.toList());

    }

    @GetMapping("service.spring")
    public List<FiveServiceSpringData> getServiceSpringInfo() {
        ServiceLoader<FiveServiceSpring> load = ServiceLoader.load(FiveServiceSpring.class);
        return load.stream()
                .map(capitalizerDryProvider -> capitalizerDryProvider.get())
                .filter(Objects::nonNull)
                .map(service -> service.findById(3))
                .collect(Collectors.toList());

    }

    @GetMapping("service.spring.injection")
    public List<FiveServiceSpringData> getServiceSpringInfoInjection() {
        log.info("Data from database {}", environment.getProperty("springapp.spring.profiles"));
        log.debug("Data from database {}", environment.getProperty("shared"));
        log.info("cloud properties {}", cloudProperties);
        log.info("instance {}", environment.getProperty("spring.cloud.zookeeper.discovery.instanceId"));
        return repository.findAll();

    }

    @GetMapping("discovery")
    public String getdiscovery() {
        registerThings();
        return serviceUrl();

    }

    public String serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances("lwbholder.setup");
        if (list != null && list.size() > 0 ) {
            return list.get(0).getUri().toString();
        }
        return null;
    }

    public void registerThings() {
        ServiceInstanceRegistration registration = ServiceInstanceRegistration.builder()
                .name("jurek")
                .defaultUriSpec()
                .address("anyUrl")
                .port(1000)
                .build();
        this.serviceRegistry.register(registration);
    }

}