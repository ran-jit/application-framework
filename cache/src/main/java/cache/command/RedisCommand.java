package cache.command;

import cache.exception.CacheException;
import redis.clients.jedis.Jedis;

public interface RedisCommand<T> {
    T execute(Jedis jedis) throws CacheException;
}
