package biz.lwb.system.holder.setup.interceptor;

import biz.lwb.system.holder.setup.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

    @Autowired
    private Producer producer;

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        producer.sendMessage(this.createMessage(request, "", ""));
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {

    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return true;
    }
}
