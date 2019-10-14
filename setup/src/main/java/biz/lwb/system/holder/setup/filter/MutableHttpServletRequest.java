package biz.lwb.system.holder.setup.filter;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

final class MutableHttpServletRequest extends HttpServletRequestWrapper {
    // holds custom header and value mapping
    private final HttpHeaders customHeaders;

    public MutableHttpServletRequest(HttpServletRequest request){
        super(request);
        this.customHeaders = new HttpHeaders();
    }

    public void putHeader(String name, String value){
        this.customHeaders.put(name, List.of(value));
    }

    public String getHeader(String name) {
        // check the custom headers first
        List<String> headers = customHeaders.get(name);

        if (headers != null && !headers.isEmpty()){
            return headers.get(0);
        }
        // else return from into the original wrapped object
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    public Enumeration<String> getHeaderNames() {
        // create a set of the custom header names
        Set<String> set = new HashSet<>(customHeaders.keySet());

        // now add the headers from the wrapped request object
        @SuppressWarnings("unchecked")
        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            // add the names of the request headers into the list
            String n = e.nextElement();
            set.add(n);
        }

        // create an enumeration from the set and return
        return Collections.enumeration(set);
    }
}
