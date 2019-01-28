package auth.filter;

import auth.utils.AuthorizationUtil;
import web.auth.AuthenticationInfo;
import web.exception.AuthenticationException;
import web.filter.SecurityFilter;

import javax.inject.Inject;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public class AuthFilter extends SecurityFilter {

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
