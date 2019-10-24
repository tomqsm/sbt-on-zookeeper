package biz.lwb.system.holder.setup.filter;

import biz.lwb.system.holder.setup.avro.HttpMethod;
import biz.lwb.system.holder.setup.avro.HttpRequestEvent;
import biz.lwb.system.holder.setup.kafka.HttpRequestEventKafkaProducer;
import biz.lwb.system.holder.setup.properties.CorrelationIdProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Order(2)
public class HttpRequestEventKafkaProducerFilter extends OncePerRequestFilter {

    @Autowired
    private HttpRequestEventKafkaProducer httpRequestEventKafkaProducer;

    @Autowired
    private CorrelationIdProperties correlationIdProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpRequestEvent requestEvent = HttpRequestEvent.newBuilder()
                .setCorrelationId(request.getHeader(correlationIdProperties.getHeaderName()))
                .setUri(request.getRequestURI())
                .setTimestamp(System.currentTimeMillis())
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(retrieveHeaders((MutableHttpServletRequest) request))
                .build();

        httpRequestEventKafkaProducer.sendMessage(requestEvent);
        filterChain.doFilter(request, response);
    }

    private Map<String, List<String>> retrieveHeaders(MutableHttpServletRequest request){
        return Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(Function.identity(), h -> Collections.list(request.getHeaders(h))));
    }

}
