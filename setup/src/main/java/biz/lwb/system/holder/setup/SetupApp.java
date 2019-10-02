package biz.lwb.system.holder.setup;

import biz.lwb.system.holder.inmemory.service.db.api.SpringConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@Import(SpringConfig.class)
@ComponentScan("biz.lwb.system.holder")
@EnableAutoConfiguration
@EnableDiscoveryClient
public class SetupApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SetupApp.class).run(args);
    }
}
