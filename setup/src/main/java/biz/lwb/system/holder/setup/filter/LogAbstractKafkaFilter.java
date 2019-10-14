package biz.lwb.system.holder.setup.filter;

import biz.lwb.system.holder.setup.kafka.LogKafkaProducer;
import biz.lwb.system.holder.setup.properties.CorrelationIdProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Order(3)
public class LogAbstractKafkaFilter extends AbstractRequestLoggingFilter {

    @Autowired
    private LogKafkaProducer logKafkaProducer;

    @Autowired
    private CorrelationIdProperties correlationIdProperties;

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        String headerName = correlationIdProperties.getHeaderName();
        logKafkaProducer.sendMessage(message.concat(String.format("%s: \"%s\"", headerName,request.getHeader(headerName))));
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
            msg.append(";headers=").append(new ServletServerHttpRequest(request).getHeaders());
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
}
