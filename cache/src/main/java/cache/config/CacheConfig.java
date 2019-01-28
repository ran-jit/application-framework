package cache.config;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import redis.clients.jedis.HostAndPort;

import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
@Getter
@Builder
@ToString
public class CacheConfig implements Serializable {
    private static final long serialVersionUID = -1438029989534898809L;

    @Builder.Default
    private ConfigType configType = ConfigType.DEFAULT;
    @Builder.Default
    private String hosts = "127.0.0.1:6379";
    @Builder.Default
    private Integer database = 0;
    private String password;
    @Builder.Default
    private Integer connectionTimeout = 2000;
    @Builder.Default
    private Integer maxActive = 10;
    @Builder.Default
    private Integer maxIdle = 5;
    @Builder.Default
    private Integer minIdle = 1;
    @Builder.Default
    private Integer numTestsPerEvictionRun = 10;
    @Builder.Default
    private Integer timeBetweenEvictionRunsMillis = 60000;
    @Builder.Default
    private Boolean testOnBorrow = Boolean.TRUE;
    @Builder.Default
    private Boolean testOnReturn = Boolean.TRUE;
    @Builder.Default
    private Boolean testWhileIdle = Boolean.TRUE;
    @Builder.Default
    private String sentinelMasterName = "mymaster";

    public enum ConfigType {
        DEFAULT, SENTINEL, CLUSTER
    }

    public Integer getRetry() {
        switch (this.configType) {
            case CLUSTER:
                return 30;
            case SENTINEL:
            default:
                return 3;
        }
    }

    public Integer getFailureWait() {
        switch (this.configType) {
            case CLUSTER:
                return 4000;
            case SENTINEL:
            default:
                return 2000;
        }
    }

    public String getHosts() {
        return hosts.replaceAll("\\s", "");
    }

    public Object getHostAndPort() {
        switch (this.configType) {
            case SENTINEL:
                return Lists.newArrayList(getHosts().split(","))
                        .stream()
                        .map(hostAndPort -> {
                            String[] hostPortArr = hostAndPort.split(":");
                            return new HostAndPort(hostPortArr[0], Integer.valueOf(hostPortArr[1])).toString();
                        }).collect(Collectors.toSet());
            case CLUSTER:
                return Lists.newArrayList(getHosts().split(","))
                        .stream()
                        .map(hostAndPort -> {
                            String[] hostPortArr = hostAndPort.split(":");
                            return new HostAndPort(hostPortArr[0], Integer.valueOf(hostPortArr[1]));
                        }).collect(Collectors.toSet());
            default:
                String[] hostAndPort = Lists.newArrayList(getHosts().split(",")).get(0).split(":");
                return new HostAndPort(hostAndPort[0], Integer.valueOf(hostAndPort[1]));
        }
    }
}
