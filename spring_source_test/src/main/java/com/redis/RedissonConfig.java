package com.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer()
                .setAddress(address);
        //config.useClusterServers()
                // use "rediss://" for SSL connection
                //.addNodeAddress(address);
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    @Bean
    public ThreadPoolExecutor taskPool(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1000, 1000, 1, TimeUnit.MINUTES, new SynchronousQueue<>()
                , new ThreadFactory() {

            private AtomicInteger count = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"TASK POOL" + count.getAndIncrement());
            }
        });
        return threadPoolExecutor;
    }
}
