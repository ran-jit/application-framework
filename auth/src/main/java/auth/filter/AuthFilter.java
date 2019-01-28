package auth.filter;

import auth.utils.AuthorizationUtil;
import com.google.inject.Inject;
import web.auth.AuthenticationInfo;
import web.exception.AuthenticationException;
import web.filter.BearerTokenFilter;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.ext.Provider;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter extends BearerTokenFilter {

    private final AuthorizationUtil authorizationUtil;

    @Inject
    public AuthFilter(AuthorizationUtil authorizationUtil) {
        this.authorizationUtil = authorizationUtil;
    }

    @Override
    protected AuthenticationInfo authenticatedUser(String authorizationValue) throws AuthenticationException {
        return this.authorizationUtil.getAuthenticationInfo(authorizationValue);
    }
}
