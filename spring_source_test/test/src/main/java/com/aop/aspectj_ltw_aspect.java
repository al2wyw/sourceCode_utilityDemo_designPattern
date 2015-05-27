package com.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.util.StopWatch;

/**
 * Created by apple on 17/05/2015.
 */
@Aspect
public class aspectj_ltw_aspect {
    @Pointcut("execution(* com.test.test_aspectj_ltw.*(..))")
    public void intercept(){}

    @Around("intercept()")
    public Object profile(ProceedingJoinPoint jp){
        StopWatch task = new StopWatch();
        task.start("profile the method "+jp.getSignature().getName());
        try{
           return jp.proceed();
        }catch (Throwable e)
        {
            e.printStackTrace();
        }finally {
            task.stop();
            System.out.println(task.prettyPrint());
        }
        return null;
    }

}
