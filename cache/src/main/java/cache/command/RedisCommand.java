package cache.command;

import cache.exception.CacheException;
import redis.clients.jedis.Jedis;

import java.io.Serializable;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public interface RedisCommand<T extends Serializable> {
    T execute(Jedis jedis) throws CacheException;
}
