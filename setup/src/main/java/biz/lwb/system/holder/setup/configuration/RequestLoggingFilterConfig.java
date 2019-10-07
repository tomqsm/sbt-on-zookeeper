package biz.lwb.system.holder.setup.configuration;

import biz.lwb.system.holder.setup.interceptor.RequestLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestLoggingFilterConfig {

    @Bean
    public RequestLoggingFilter logFilter() {
        RequestLoggingFilter filter = new RequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }

}
