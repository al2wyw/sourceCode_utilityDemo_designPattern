package com.dynamicInvoke;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/5/24
 * Time: 10:57
 * Desc:
 */
public class DynamicTest {

    public static void main(final String[] args) throws Exception {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Class klass = cl.loadClass("com.dynamicInvoke.Dynamic");
        Method main = klass.getDeclaredMethod("main", String[].class);
        main.invoke(null,(Object)null);
    }
}
