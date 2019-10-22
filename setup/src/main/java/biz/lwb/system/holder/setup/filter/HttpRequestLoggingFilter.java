package biz.lwb.system.holder.setup.filter;

import biz.lwb.system.holder.setup.avro.HttpMethod;
import biz.lwb.system.holder.setup.avro.HttpRequestEvent;
import biz.lwb.system.holder.setup.kafka.HttpRequestEventKafkaProducer;
import biz.lwb.system.holder.setup.properties.CorrelationIdProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Component
@Order(3)
@Slf4j
public class HttpRequestLoggingFilter extends AbstractRequestLoggingFilter {

    @Autowired
    private HttpRequestEventKafkaProducer httpRequestEventKafkaProducer;

    @Autowired
    private CorrelationIdProperties correlationIdProperties;

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        log.info("request", createMessage(request, "BEGIN", "END"));
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {

    }
    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return true;
    }

    @Override
    protected boolean isIncludeClientInfo() {
        return true;
    }

    @Override
    protected boolean isIncludeHeaders() {
        return true;
    }

    @Override
    protected boolean isIncludePayload() {
        return true;
    }

    @Override
    protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
        StringBuilder msg = new StringBuilder();
        msg.append(prefix);
        msg.append("uri=").append(request.getRequestURI());

        if (isIncludeQueryString()) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                msg.append('?').append(queryString);
            }
        }

        if (isIncludeClientInfo()) {
            String client = request.getRemoteAddr();
            if (StringUtils.hasLength(client)) {
                msg.append(";client=").append(client);
            }
            HttpSession session = request.getSession(false);
            if (session != null) {
                msg.append(";session=").append(session.getId());
            }
            String user = request.getRemoteUser();
            if (user != null) {
                msg.append(";user=").append(user);
            }
        }

        if (isIncludeHeaders()) {
            msg.append(";headers=").append(msg.append(";headers=").append(new ServletServerHttpRequest(request) {
                @Override
                public HttpHeaders getHeaders() {
                    String headerKey = correlationIdProperties.getHeaderName();
                    String x_correlation = getServletRequest().getHeader(headerKey);

                    HttpHeaders headers = super.getHeaders();
                    headers.put(headerKey, List.of(x_correlation));
                    return headers;
                }
            }.getHeaders()));
        }

        if (isIncludePayload()) {
            String payload = getMessagePayload(request);
            if (payload != null) {
                msg.append(";payload=").append(payload);
            }
        }

        msg.append(suffix);
        return msg.toString();
    }

    @Override
    protected boolean isIncludeQueryString() {
        return true;
    }


}
