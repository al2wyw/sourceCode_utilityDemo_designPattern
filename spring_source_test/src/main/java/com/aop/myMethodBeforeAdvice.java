package com.aop;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by apple on 11/05/2015.
 */
public class myMethodBeforeAdvice implements MethodBeforeAdvice {

    boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        if (flag) {
            System.out.println("aop with flag set to true");
        }
    }
}
