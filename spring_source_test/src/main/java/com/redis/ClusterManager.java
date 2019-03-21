package com.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/3/18
 * Time: 18:10
 * Desc:
 */
@Component
public class ClusterManager {

    @Value("${redis.cluster.address1}")
    private String address1;

    @Value("${redis.cluster.address2}")
    private String address2;

    @Value("${redis.cluster.address3}")
    private String address3;

    @Value("${redis.pool.size}")
    private int poolSize;

    private RedissonClient redissonClient;

    public RedissonClient getRedissonClient() {
        if(redissonClient == null) {
            Config config = buildConfig(null);
            this.redissonClient = Redisson.create(config);
        }
        return redissonClient;
    }

    public void rehash(String address){
        RedissonClient old = redissonClient;
        Config config = buildConfig(address);
        this.redissonClient = Redisson.create(config);
        old.shutdown();
    }

    private Config buildConfig(String address){
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        ClusterServersConfig clusterServersConfig = config.useClusterServers();
        clusterServersConfig.setScanInterval(1000) //do not interfere with capture data
                .setReadMode(ReadMode.MASTER)
                .setMasterConnectionPoolSize(poolSize)
                .setMasterConnectionMinimumIdleSize(poolSize)
                //.setSlaveConnectionMinimumIdleSize(poolSize)
                //.setSlaveConnectionPoolSize(poolSize)
                .addNodeAddress(address1)
                .addNodeAddress(address2)
                .addNodeAddress(address3);

        if(address != null) {
            clusterServersConfig.addNodeAddress(address);
        }
        return config;
    }
}
