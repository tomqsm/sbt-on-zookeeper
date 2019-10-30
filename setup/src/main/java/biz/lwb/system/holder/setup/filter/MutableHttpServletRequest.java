package biz.lwb.system.holder.setup.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;

final class MutableHttpServletRequest extends HttpServletRequestWrapper {

    private final HttpHeaders customHeaders;
    private static final String DELIMITER = ",";

    public MutableHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HttpHeaders();
    }

    public void putHeader(String name, String value) {
        this.customHeaders.put(name, List.of(value));
    }

    @Override
    public String getHeader(String name) {
        String header = ((HttpServletRequest) getRequest()).getHeader(name);
        if (StringUtils.isBlank(header)) {
            List<String> strings = customHeaders.get(name);
            List<String> headers = strings == null || strings.isEmpty() ? List.of(EMPTY) : strings;
            header = String.join(DELIMITER, headers);
        }
        return header;
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        addRequestHeaders();
        List<String> c = customHeaders.get(name);
        return Collections.enumeration(c == null ? Collections.emptyList() : c);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        addRequestHeaders();
        return Collections.enumeration(customHeaders.keySet());
    }

    private void addRequestHeaders() {
        HttpServletRequest request = (HttpServletRequest) getRequest();
        Enumeration<String> requestHeaderNames = request.getHeaderNames();
        requestHeaderNames.asIterator().forEachRemaining(s -> {
            if (!StringUtils.equalsIgnoreCase(request.getHeader(s), customHeaders.getFirst(s))) {
                customHeaders.add(s, request.getHeader(s));
            }
        });
    }
}
