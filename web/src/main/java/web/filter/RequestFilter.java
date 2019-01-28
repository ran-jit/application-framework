package web.filter;

import com.google.common.collect.Sets;
import com.google.common.net.HttpHeaders;
import web.auth.AuthenticationInfo;
import web.context.RequestContext;
import web.exception.AuthenticationException;
import web.request.RequestInfo;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public abstract class RequestFilter implements Filter {

    private static final String SERVER_REQUEST_ID = "X-Request-ID";

    private final Set<String> byPassURIs;

    public RequestFilter() {
        this(null);
    }

    public RequestFilter(Set<String> byPassURIs) {
        this.byPassURIs = (byPassURIs == null) ? Sets.newHashSet() : byPassURIs;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (this.byPassURIs.contains(((HttpServletRequest) servletRequest).getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
        }

        try {
            String requestId = String.format("Req-%s", System.currentTimeMillis());
            ((HttpServletResponse) servletResponse).setHeader(SERVER_REQUEST_ID, requestId);

            AuthenticationInfo authenticationInfo = authenticatedUser(((HttpServletRequest) servletRequest).getHeader(HttpHeaders.AUTHORIZATION));
            RequestContext.set(RequestInfo.builder()
                    .requestId(requestId)
                    .authenticationInfo(authenticationInfo)
                    .build());

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (AuthenticationException ex) {
            handleAuthenticationException(ex);
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } finally {
            RequestContext.remove();
        }
    }

    protected abstract AuthenticationInfo authenticatedUser(String authorizationData) throws AuthenticationException;

    protected abstract void handleAuthenticationException(AuthenticationException ex);
}
