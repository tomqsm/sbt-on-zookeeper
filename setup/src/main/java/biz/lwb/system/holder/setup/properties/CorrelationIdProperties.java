package biz.lwb.system.holder.setup.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@ToString
@Configuration
@ConfigurationProperties(prefix = "correlation-id")
@RefreshScope
public class CorrelationIdProperties {
    String headerName;
    String mdcName;
}
