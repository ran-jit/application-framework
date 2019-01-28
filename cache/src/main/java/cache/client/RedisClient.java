package cache.client;

import cache.command.RedisClusterCommand;
import cache.command.RedisCommand;
import cache.config.CacheConfig;
import cache.exception.CacheException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.JedisPoolConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class RedisClient {

    static Logger LOGGER = Logger.getLogger(RedisClient.class.getName());

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    final int retry;
    private final int failureWait;
    protected final CacheConfig config;

    RedisClient(CacheConfig config, int retry, int failureWait) {
        this.config = config;
        this.retry = retry;
        this.failureWait = failureWait;
    }

    JedisPoolConfig poolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(this.config.getMaxIdle());
        poolConfig.setMinIdle(this.config.getMinIdle());
        poolConfig.setMaxTotal(this.config.getMaxActive());
        poolConfig.setTestOnBorrow(this.config.getTestOnBorrow());
        poolConfig.setTestOnReturn(this.config.getTestOnReturn());
        poolConfig.setTestWhileIdle(this.config.getTestWhileIdle());
        poolConfig.setNumTestsPerEvictionRun(this.config.getNumTestsPerEvictionRun());
        poolConfig.setTimeBetweenEvictionRunsMillis(this.config.getTimeBetweenEvictionRunsMillis());
        return poolConfig;
    }

    void handleException(int tries, RuntimeException ex) throws CacheException {
        LOGGER.log(Level.SEVERE, "Jedis connection failed, retrying... {0}", tries);
        if (tries == this.retry) {
            throw new CacheException(ex.getMessage(), ex);
        }
        try {
            Thread.sleep(this.failureWait);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public abstract <K> K execute(RedisCommand<K> command) throws CacheException;

    public abstract void executeAsync(RedisCommand command);

    public abstract <K> K execute(RedisClusterCommand<K> clusterCommand) throws CacheException;

    public abstract void executeAsync(RedisClusterCommand clusterCommand);

    public abstract <K> K execute(RedisCommand<K> command, RedisClusterCommand<K> clusterCommand) throws CacheException;

    public abstract void executeAsync(RedisCommand command, RedisClusterCommand clusterCommand);

}
