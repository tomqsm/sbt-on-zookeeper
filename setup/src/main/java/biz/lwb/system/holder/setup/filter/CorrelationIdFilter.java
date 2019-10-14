package biz.lwb.system.holder.setup.filter;

import biz.lwb.system.holder.setup.properties.CorrelationIdProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.apache.commons.lang.StringUtils.isNotEmpty;


@Data
@EqualsAndHashCode(callSuper = false)
@Component
@Order(1)
public class CorrelationIdFilter extends OncePerRequestFilter {

    private final CorrelationIdProperties correlationIdProperties;

    public CorrelationIdFilter(CorrelationIdProperties correlationIdProperties) {
        this.correlationIdProperties = correlationIdProperties;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws java.io.IOException, ServletException {
        MutableHttpServletRequest mutableHttpServletRequest = new MutableHttpServletRequest(request);
        try {
            final String correlationId = extractOrMakeCorrelationId(request);
            MDC.put(correlationIdProperties.getMdcName(), correlationId);
            String headerName = correlationIdProperties.getHeaderName();
            mutableHttpServletRequest.putHeader(headerName, correlationId);
            response.addHeader(headerName, correlationId);
            chain.doFilter(mutableHttpServletRequest, response);
        } finally {
            MDC.remove(correlationIdProperties.getMdcName());
        }
    }

    private String extractOrMakeCorrelationId(final HttpServletRequest request) {
        final String correlationId;
        String headerName = correlationIdProperties.getHeaderName();
        String correlationIdHeader = request.getHeader(headerName);
        if (isNotEmpty(correlationIdHeader)) {
            correlationId = correlationIdHeader;
        } else {
            correlationId = UUID.randomUUID().toString().replace("-", "");
        }
        return correlationId;
    }

    private String extractClientIP(final HttpServletRequest request) {
        final String clientIP;
        if (request.getHeader("X-Forwarded-For") != null) {
            clientIP = request.getHeader("X-Forwarded-For").split(",")[0];
        } else {
            clientIP = request.getRemoteAddr();
        }
        return clientIP;
    }

    @Override
    protected boolean isAsyncDispatch(final HttpServletRequest request) {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}
