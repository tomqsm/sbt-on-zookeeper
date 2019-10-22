package biz.lwb.system.holder.setup.controller;

import biz.lwb.system.holder.inmemory.service.db.dto.MonitorDto;
import biz.lwb.system.holder.inmemory.service.db.mapper.MonitorDao;
import biz.lwb.system.holder.setup.avro.HttpRequestAvro;
import biz.lwb.system.holder.setup.kafka.KafkaHttpRequestPollingConsumer;
import biz.lwb.system.holder.setup.kafka.KafkaHttpRequestProducer;
import biz.lwb.system.holder.setup.properties.LadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class IndexController {

    @Autowired
    private MonitorDao monitorDao;

    @Autowired
    private Environment environment;

    @Autowired
    private LadProperties cloudProperties;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ZookeeperServiceRegistry serviceRegistry;

    @Autowired
    private KafkaHttpRequestProducer kafkaHttpRequestProducer;

    @Autowired
    private KafkaHttpRequestPollingConsumer kafkaHttpRequestPollingConsumer;

    @GetMapping("service.spring.injection")
    public List<MonitorDto> getServiceSpringInfoInjection(HttpServletRequest httpRequest) {

        log.info("Data from database {}", environment.getProperty("springapp.spring.profiles"));
        log.debug("Data from database {}", environment.getProperty("shared"));
        log.error("cloud properties {}", cloudProperties);
        log.info("instance {}", environment.getProperty("spring.cloud.zookeeper.discovery.instanceId"));
        return monitorDao.findAll();

    }

    @GetMapping("discovery")
    public String getdiscovery() {
        registerThings();
        return serviceUrl();

    }

    @GetMapping("kafka")
    public CompletableFuture<? extends ConsumerRecords<? super String, ? super GenericRecord>> getKafka() throws ExecutionException, InterruptedException {
        HttpRequestAvro input = HttpRequestAvro.newBuilder()
                .setFirst("first " + System.currentTimeMillis())
                .setSecond("second " + System.currentTimeMillis())
                .build();
        kafkaHttpRequestProducer.sendMessage(input);

        return kafkaHttpRequestPollingConsumer.consume().completeAsync(() -> null);

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