package auth.utils;

import auth.cache.AuthCache;
import cache.exception.CacheException;
import web.auth.AuthenticationInfo;
import web.exception.AuthenticationException;

import javax.inject.Inject;

import static web.exception.AuthenticationException.AuthenticationFailiureStatus.INVALID_ACCESS_TOKEN;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public class AuthorizationUtil {

    private final AuthCache authCache;

    @Inject
    public AuthorizationUtil(AuthCache authCache) {
        this.authCache = authCache;
    }

    public AuthenticationInfo getAuthenticationInfo(String authorizationValue) throws AuthenticationException {
        String[] values = authorizationValue.split(" ");
        if (values.length == 2) {
            AuthenticationInfo authenticationInfo;
            try {
                authenticationInfo = this.authCache.get(values[1]);
            } catch (CacheException ex) {
                throw new AuthenticationException(INVALID_ACCESS_TOKEN);
            }
            return authenticationInfo;
        }
        throw new AuthenticationException(INVALID_ACCESS_TOKEN);
    }
}
