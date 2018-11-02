package com.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/1/15
 * Time: 17:30
 * Desc:
 */
public class testChangeClassStruct {
    public static void main( String args[] ) throws Exception{
        startTreadA();
        //Thread.sleep(3000);
        startTreadB();
    }

    public static void startTreadA(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ClassPool pool = ClassPool.getDefault();
                    CtClass cc = pool.makeClass("com.samples.Programmer");
                    //定义code方法
                    CtMethod method = CtNewMethod.make("public void code(){}", cc);
                    //插入方法代码
                    method.insertBefore("System.out.println(\"I'm a Programmer,Nothing A.....\");");
                    cc.addMethod(method);
                    Class clazz = cc.toClass();
                    testChangeClassStruct test = new testChangeClassStruct();
                    if(test.getClass().isAssignableFrom(clazz)){
                        System.out.println("son A");
                    }
                    invokeMethod(clazz);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t.setContextClassLoader(new MyClassLoader(Thread.currentThread().getContextClassLoader()));
        t.start();
    }

    public static void startTreadB(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ClassPool pool = ClassPool.getDefault();
                    CtClass cc1 = pool.getCtClass("com.javassist.testChangeClassStruct");

                    CtClass cc = pool.makeClass("com.samples.Programmer",cc1);
                    //定义code方法
                    CtMethod method = CtNewMethod.make("public void code(){}", cc);
                    //插入方法代码
                    method.insertBefore("super.code(); System.out.println(\"I'm a Programmer,Nothing B.....\");");
                    cc.addMethod(method);
                    Class clazz = cc.toClass();
                    testChangeClassStruct test = new testChangeClassStruct();
                    if(test.getClass().isAssignableFrom(clazz)){
                        System.out.println("son B");
                    }
                    invokeMethod(clazz);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t.setContextClassLoader(new MyClassLoader(Thread.currentThread().getContextClassLoader()));
        t.start();
    }

    public void code(){
        System.out.println("I'm a Programmer,Main.....");
    }

    public static void invokeMethod(Class clazz) throws Exception{
        Object o = clazz.newInstance();
        Method method1 = clazz.getMethod("code",null);
        method1.invoke(o, null);
    }
}
