package cache.client;

import cache.command.RedisClusterCommand;
import cache.command.RedisCommand;
import cache.config.CacheConfig;
import cache.exception.CacheException;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.util.Pool;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public class RedisClientImpl extends RedisClient {

    private final Pool<Jedis> jedisPool;
    private final JedisCluster jedisCluster;

    private static final int DEFAULT_MAX_REDIRECTIONS = 5;

    public RedisClientImpl(CacheConfig config) {
        super(config, config.getRetry(), config.getFailureWait());
        switch (config.getConfigType()) {
            case CLUSTER:
                this.jedisPool = null;
                this.jedisCluster = new JedisCluster((Set<HostAndPort>) config.getHostAndPort(), config.getConnectionTimeout(), Protocol.DEFAULT_TIMEOUT, DEFAULT_MAX_REDIRECTIONS, config.getPassword(), poolConfig());
                break;
            case SENTINEL:
                this.jedisCluster = null;
                this.jedisPool = new JedisSentinelPool(config.getSentinelMasterName(), (Set<String>) config.getHostAndPort(), poolConfig(), config.getConnectionTimeout(), config.getPassword(), config.getDatabase());
                break;
            default:
                this.jedisCluster = null;
                HostAndPort hostAndPort = (HostAndPort) config.getHostAndPort();
                this.jedisPool = new JedisPool(poolConfig(), hostAndPort.getHost(), hostAndPort.getPort(), config.getConnectionTimeout(), config.getPassword(), config.getDatabase());
                break;
        }
    }

    @Override
    public <V extends Serializable> V execute(RedisCommand<V> command) throws CacheException {
        int tries = 0;
        boolean retry = true;
        V value = null;
        do {
            tries++;
            try (Jedis jedis = this.jedisPool.getResource()) {
                value = command.execute(jedis);
                retry = false;
            } catch (JedisException ex) {
                handleException(tries, ex);
            }
        } while (retry && tries <= this.retry);
        return value;
    }

    @Override
    public void executeAsync(RedisCommand command) {
        CompletableFuture.runAsync(() -> {
            try {
                int tries = 0;
                boolean retry = true;
                do {
                    tries++;
                    try (Jedis jedis = this.jedisPool.getResource()) {
                        command.execute(jedis);
                        retry = false;
                    } catch (JedisException ex) {
                        handleException(tries, ex);
                    }
                } while (retry && tries <= this.retry);
            } catch (CacheException ex) {
                LOGGER.log(Level.SEVERE, "Error executing async redis command", ex);
            }
        });
    }

    @Override
    public <V extends Serializable> V execute(RedisClusterCommand<V> clusterCommand) throws CacheException {
        int tries = 0;
        boolean retry = true;
        V value = null;
        do {
            tries++;
            try {
                value = clusterCommand.execute(this.jedisCluster);
                retry = false;
            } catch (JedisException ex) {
                handleException(tries, ex);
            }
        } while (retry && tries <= this.retry);
        return value;
    }

    @Override
    public void executeAsync(RedisClusterCommand clusterCommand) {
        CompletableFuture.runAsync(() -> {
            try {
                int tries = 0;
                boolean retry = true;
                do {
                    tries++;
                    try {
                        clusterCommand.execute(this.jedisCluster);
                        retry = false;
                    } catch (JedisException ex) {
                        handleException(tries, ex);
                    }
                } while (retry && tries <= this.retry);
            } catch (CacheException ex) {
                LOGGER.log(Level.SEVERE, "Error executing async redis cluster command", ex);
            }
        });
    }

    @Override
    public <V extends Serializable> V execute(RedisCommand<V> command, RedisClusterCommand<V> clusterCommand) throws CacheException {
        switch (config.getConfigType()) {
            case CLUSTER:
                return execute(clusterCommand);
            case SENTINEL:
            default:
                return execute(command);
        }
    }

    @Override
    public void executeAsync(RedisCommand command, RedisClusterCommand clusterCommand) {
        switch (config.getConfigType()) {
            case CLUSTER:
                executeAsync(clusterCommand);
            case SENTINEL:
            default:
                executeAsync(command);
        }
    }
}
