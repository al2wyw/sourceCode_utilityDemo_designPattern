package com.stable;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.utils.LoggerUtils;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/11/29
 * Time: 15:56
 * Desc:
 */
@Service
public class StableService {

    @Retryable(value=IllegalStateException.class, maxAttempts = 5, backoff = @Backoff(1000))
    public String get(int i){
        LoggerUtils.getLogger().info("get call");
        if(i == 0){
            throw new IllegalStateException("test");
        }
        return "test";
    }

    //异常兜底,原来函数的返回值会被替换，第一个必须是异常类型，其他参数是原函数的参数
    @Recover()
    public String recover(IllegalStateException e, int i){
        LoggerUtils.getLogger().error("recover call i=" + i, e);
        return "recover";
    }

    //默认是stateful, 出现异常直接抛出没有重试
    @CircuitBreaker(value=IllegalStateException.class, openTimeout = 3000)
    public String circuit(int i){
        LoggerUtils.getLogger().info("circuit call");
        if(i == 0){
            try{
                Thread.sleep(500);
            }catch (Exception e){
                LoggerUtils.getLogger().error("",e);
            }
            throw new IllegalStateException("circuit");
        }
        return "test";
    }

    /**
     * Hystrix test start
     * */

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20")
    },fallbackMethod = "testFallback")
    public String test(int i){
        LoggerUtils.getLogger().info("test call");
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            LoggerUtils.getLogger().error("",e);
        }
        return "test";
    }

    //相同的方法签名和返回类型，方法名可以不一样
    public String testFallback(int i){
        return "testFallback";
    }
}
