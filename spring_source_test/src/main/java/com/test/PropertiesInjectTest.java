package com.test;

import org.springframework.context.annotation.Lazy;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/11/27
 * Time: 20:03
 * Desc:
 */
public class PropertiesInjectTest {

    private testScopedProxy testScopedProxy; //scoped proxy will be injected

    private LazyWithoutAOP lazyWithoutAOP;

    public void setTestScopedProxy(testScopedProxy testScopedProxy) {
        this.testScopedProxy = testScopedProxy;
    }

    @Lazy //lazy proxy will be injected
    public void setLazyWithoutAOP(LazyWithoutAOP lazyWithoutAOP) {
        this.lazyWithoutAOP = lazyWithoutAOP;
    }
}
