package com.aop;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/12/31
 * Time: 17:14
 * Desc:
 */
public class testReflect {

    public static void main(String[] args) throws Exception{
        Method method = testInterface.class.getDeclaredMethod("testMethod");//MethodAccessor invokeInterface
        Method method1 = testImpl.class.getDeclaredMethod("testMethod");//MethodAccessor invokeVirtual
        testImpl test = new testImpl();
        method.invoke(test);
    }
}
