package biz.lwb.system.holder.inmemory.service.db.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Slf4j
@Configuration
@PropertySource("classpath:application-inmemory.yml")
public class InmemDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.inmemory-datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("inmemDataSource")
    @DependsOn("dataSourceProperties")
    public DataSource getInmemDataSource(DataSourceProperties dataSourceProperties) {
        log.info("Initializing datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

}
