package biz.lwb.system.holder.inmemory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@ComponentScan("biz.lwb.system.holder.inmemory.service")
@Profile("inmemory")
@PropertySource("classpath:application-inmemory.yml")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class SpringConfig {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    @Qualifier("inmemDataSource")
    private DataSource inmemDataSource;

    @EventListener
    public void preloadInmemoryStore(@SuppressWarnings("UnusedParameters") ContextRefreshedEvent event) {
        Environment environment = event.getApplicationContext().getEnvironment();
        String propertyName = "spring.inmemory-datasource";
        if ("true".equals(environment.getProperty(propertyName + ".initialize"))) {
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
            addNotEmptyResource(environment, databasePopulator, propertyName + ".schema");
            addNotEmptyResource(environment, databasePopulator, propertyName + ".data");
            DatabasePopulatorUtils.execute(databasePopulator, inmemDataSource);
        }

    }

    private void addNotEmptyResource(Environment environment, ResourceDatabasePopulator populator, String location) {
        String uri = environment.getProperty(location);
        if (isNotBlank(uri)) {
            final Resource resource = resourceLoader.getResource(uri);
            populator.addScript(resource);
        }
    }
}
