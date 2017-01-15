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
 * Time: 0:19
 * Desc:
 */
public class Main {
    public static void main( String args[] ) throws Exception{
        startTread("Coding");//correct
        startTread("Sleeping");//correct

        testSameClass();//error
    }

    public static void startTread(final String action){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ClassPool pool = ClassPool.getDefault();
                    CtClass cc = pool.makeClass("com.samples.Programmer");
                    //定义code方法
                    CtMethod method = CtNewMethod.make("public void code(){}", cc);//直接把字符码parse成字节码,不用javac
                    //插入方法代码
                    method.insertBefore("System.out.println(\"I'm a Programmer,Just "+ action +".....\");");
                    cc.addMethod(method);
                    invokeMethod(cc);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t.setContextClassLoader(new MyClassLoader(Thread.currentThread().getContextClassLoader()));//set different classloader
        t.start();
    }

    public static void invokeMethod(CtClass cc) throws Exception{
        Class clazz = cc.toClass();//invoke ClassLoader.defineClass by reflection
        Object o = clazz.newInstance();
        Method method1 = clazz.getMethod("code",null);
        method1.invoke(o, null);
    }

    public static void testSameClass() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("com.samples.Programmer");
        //定义code方法
        CtMethod method = CtNewMethod.make("public void code(){}", cc);//直接把字符码parse成字节码,不用javac
        //插入方法代码
        method.insertBefore("System.out.println(\"I'm a Programmer,Just Sleeping and Coding.....\");");
        cc.addMethod(method);
        invokeMethod(cc);

        ClassPool pool1 = ClassPool.getDefault();
        CtClass cc1 = pool1.makeClass("com.samples.Programmer");//com.samples.Programmer: frozen class (cannot edit)
        //定义code方法
        CtMethod method1 = CtNewMethod.make("public void code(){}", cc1);
        //插入方法代码
        method1.insertBefore("System.out.println(\"I'm a Programmer,Just Coding and Sleeping.....\");");
        cc1.addMethod(method1);
        invokeMethod(cc1);
    }
}
