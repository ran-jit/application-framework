package auth.cache;

import cache.client.RedisClient;
import cache.client.RedisClientImpl;
import cache.config.CacheConfig;
import cache.exception.CacheException;
import web.auth.AuthenticationInfo;

import javax.inject.Inject;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public class AuthCache {

    private static final String AUTH_CACHE_KEY = "AUTH_INFO";

    private final RedisClient client;

    @Inject
    public AuthCache(CacheConfig config) {
        this.client = new RedisClientImpl(config);
    }

    public void put(AuthenticationInfo authenticationInfo) throws CacheException {
        try {
            byte[] value = RedisClient.OBJECT_MAPPER.writeValueAsBytes(authenticationInfo);
            this.client.executeAsync(jedis -> jedis.hset(AUTH_CACHE_KEY.getBytes(), authenticationInfo.getAccessToken().getBytes(), value),
                    jedisCluster -> jedisCluster.hset(AUTH_CACHE_KEY.getBytes(), authenticationInfo.getAccessToken().getBytes(), value));

        } catch (Exception ex) {
            throw new CacheException(ex.getMessage());
        }
    }

    public AuthenticationInfo get(String accessToken) throws CacheException {
        try {
            Object obj = this.client.execute(jedis -> jedis.hget(AUTH_CACHE_KEY, accessToken),
                    jedisCluster -> jedisCluster.hget(AUTH_CACHE_KEY, accessToken));

            if (obj == null) {
                throw new CacheException("Invalid access token");
            }

            return RedisClient.OBJECT_MAPPER.readValue((byte[]) obj, AuthenticationInfo.class);
        } catch (Exception ex) {
            throw new CacheException(ex.getMessage());
        }
    }

    public Boolean contains(String accessToken) throws CacheException {
        return this.client.execute(jedis -> jedis.hexists(AUTH_CACHE_KEY, accessToken),
                jedisCluster -> jedisCluster.hexists(AUTH_CACHE_KEY, accessToken));
    }
}
