package cache;

import cache.client.RedisClient;
import cache.client.RedisClientImpl;
import cache.config.CacheConfig;
import cache.exception.CacheException;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public class Test {

    public static void main(String[] args) throws CacheException {
        RedisClient client = new RedisClientImpl(CacheConfig.builder().configType(CacheConfig.ConfigType.DEFAULT).build());
        Object obj = client.execute(jedis -> jedis.get("abc"), jedisCluster -> jedisCluster.get("abc"));
    }
}
