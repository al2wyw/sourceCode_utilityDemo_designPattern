package com.stable;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/11/29
 * Time: 17:17
 * Desc:
 */
@EnableRetry
@Configuration
public class StableConfig {

    @Bean
    public HystrixCommandAspect HystrixCommandAspect(){
        return new HystrixCommandAspect();
    }
}
