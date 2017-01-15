package com.javac;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/1/15
 * Time: 14:57
 * Desc:
 */
public class Main {
    public static void main( String args[] ) throws Exception{
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream in = cl.getResourceAsStream("./com/javac/Main.class");
        byte[] code = new byte[1024*128];
        int len = in.read(code);
        byte[] byteCodes = Arrays.copyOf(code,len);
        MyClassLoader classLoader = new MyClassLoader(cl);
        classLoader.defineClass("com.javac.Main",byteCodes);
        //classLoader.defineClass("com.javac.Main",byteCodes);//error
        MyClassLoader classLoader1 = new MyClassLoader(cl);
        Class mainclass = classLoader1.defineClass("com.javac.Main",byteCodes);
        Object main = mainclass.newInstance();
        Method hello = mainclass.getMethod("hello", null);
        hello.invoke(main,null);
    }

    public void hello(){
        System.out.println("hello");
    }
}
