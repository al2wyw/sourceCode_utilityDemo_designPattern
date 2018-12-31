package com.aop;

import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/12/30
 * Time: 12:21
 * Desc:
 */
@Component("testItf")
public class testImpl implements testInterface {

    @Override
    public void testMethod() {
        System.out.println("testMethod is called");
    }
}
