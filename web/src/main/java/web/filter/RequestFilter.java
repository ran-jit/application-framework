package web.filter;

import web.context.RequestContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public class RequestFilter implements Filter {

    private static final String SERVER_REQUEST_ID = "X-Request-ID";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            String requestId = String.format("Req-%s", System.currentTimeMillis());

            RequestContext.get().setRequestId(requestId);
            ((HttpServletResponse) servletResponse).setHeader(SERVER_REQUEST_ID, requestId);

        } finally {
            RequestContext.remove();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
