package com.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/3/13
 * Time: 20:39
 * Desc:
 */
@Configuration
public class RedissonConfig {

    @Value("${redis.address}")
    private String address;

    @Bean
    public RedissonClient getRedissonClient(){
        Config config = new Config();
        config.setTransportMode(TransportMode.EPOLL);
        config.useClusterServers()
                // use "rediss://" for SSL connection
                .addNodeAddress(address);
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
