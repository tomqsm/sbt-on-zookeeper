package biz.lwb.system.holder.setup.interceptor;

import biz.lwb.system.holder.setup.kafka.LogKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

public class LogRequestKafkaFilter extends AbstractRequestLoggingFilter {

    @Autowired
    private LogKafkaProducer logKafkaProducer;

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        logKafkaProducer.sendMessage(this.createMessage(request, "", ""));
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {

    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return true;
    }
}
