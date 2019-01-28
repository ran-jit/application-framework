package cache.command;

import cache.exception.CacheException;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public interface RedisClusterCommand<T extends Serializable> {
    T execute(JedisCluster jedisCluster) throws CacheException;
}
