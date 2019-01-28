package cache.command;

import cache.exception.CacheException;
import redis.clients.jedis.JedisCluster;

public interface RedisClusterCommand<T> {
    T execute(JedisCluster jedisCluster) throws CacheException;
}
