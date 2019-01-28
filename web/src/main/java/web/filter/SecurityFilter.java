package web.filter;

import com.google.common.collect.Sets;
import web.auth.AuthenticationInfo;
import web.context.RequestContext;
import web.exception.AuthenticationException;
import web.status.Status;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Set;

public abstract class SecurityFilter implements ContainerRequestFilter {

    private final Set<String> byPassURIs;

    public SecurityFilter() {
        this(null);
    }

    public SecurityFilter(Set<String> byPassURIs) {
        this.byPassURIs = (byPassURIs == null) ? Sets.newHashSet() : byPassURIs;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (this.byPassURIs.contains(requestContext.getUriInfo().getPath())) {
            return;
        }

        try {
            AuthenticationInfo authenticationInfo = authenticatedUser(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION));
            RequestContext.get().setAuthenticationInfo(authenticationInfo);

        } catch (AuthenticationException ex) {
            web.response.Response response = web.response.Response.builder()
                    .status(Status.builder()
                            .statusCode(ex.getStatusCode())
                            .success(Boolean.FALSE)
                            .type(Status.Type.ERROR)
                            .build())
                    .build();

            requestContext.abortWith(Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(response)
                    .type(MediaType.APPLICATION_JSON).build());
        }
    }

    protected abstract AuthenticationInfo authenticatedUser(String authorizationValue) throws AuthenticationException;
}
