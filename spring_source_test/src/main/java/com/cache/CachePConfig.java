package com.cache;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/10/29
 * Time: 10:31
 * Desc:
 */
@Configuration
public class CachePConfig {

    @Bean
    public CacheManager cacheManager(){
        GuavaCacheManager manager = new GuavaCacheManager();
        manager.setCacheBuilder(CacheBuilder.newBuilder()
                .concurrencyLevel(8)
                .expireAfterAccess(10, TimeUnit.SECONDS)
        );
        return manager;
    }
}
