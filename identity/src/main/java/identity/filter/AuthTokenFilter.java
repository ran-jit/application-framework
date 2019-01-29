package identity.filter;

import identity.model.AuthenticationInfo;
import identity.model.FilterURIs;
import identity.utils.AuthorizationUtil;
import com.google.inject.Inject;
import web.context.RequestContext;
import web.exception.AuthenticationException;
import web.status.Status;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthTokenFilter implements ContainerRequestFilter {

    private final FilterURIs filterURIs;
    private final AuthorizationUtil authorizationUtil;

    @Inject
    public AuthTokenFilter(AuthorizationUtil authorizationUtil, FilterURIs filterURIs) {
        this.authorizationUtil = authorizationUtil;
        this.filterURIs = filterURIs;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (this.filterURIs.getByPassURIs().contains(requestContext.getUriInfo().getPath())) {
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

    private AuthenticationInfo authenticatedUser(String authorizationValue) throws AuthenticationException {
        return this.authorizationUtil.getAuthenticationInfo(authorizationValue);
    }
}
