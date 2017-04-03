package com.classLoaderPath;

import java.io.File;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/1/25
 * Time: 16:05
 * Desc:
 */
public class ClassLoadError {
    /**
     * ClassNotFoundException 加载类的时候找不到class文件，一般是classpath设置不对，或者是缺少依赖
     * NoClassDefFoundError 不是加载类的时候报的错误，而是在初始化的时候报的错误， 当需要寻找一个类的Class对象的时候，在当前命名空间找不到这个Class对象，那么会抛出这个错误
     * ClassCastException 对象强制转换的时候，目标Class已经找到了，但是与需要被转的对象的类加载不一样，导致转换出错
     * */
    public static void main(String[] args) throws Exception{
        test1();
        System.out.println("-------------------------------");
        test2();
        System.out.println("-------------------------------");
        test3();
    }
    public static void test1() {
        System.out.println(System.getProperty("java.class.path"));
        try {
            ClassLoader.getSystemClassLoader().loadClass("com.test.NotFound");//not exist
        } catch(Throwable th) {
            th.printStackTrace();
        }
    }

    // NoClassDefFoundError
    public static void test2() throws Exception {
        BizClassLoader cl1 = new BizClassLoader(new URL[] { new File(findTarget()).toURI().toURL()});

        Class<?>  clazz2 = cl1.loadClass(NoClassDef.class.getName());
        System.out.println(clazz2.getClassLoader());
        try {
            clazz2.newInstance();
        } catch(Throwable th) {
            th.printStackTrace();
        }
    }

    // ClassCastException
    public static void test3() throws Exception {
        BizClassLoader cl1 = new BizClassLoader(new URL[] { new File(findTarget()).toURI().toURL()});
        Class<?>  clazz1 = CastExp.class;
        Class<?>  clazz2 = cl1.loadClass(CastExp.class.getName());
        try {
            CastExp castExp = (CastExp)clazz2.newInstance();
        } catch(Throwable th) {
            th.printStackTrace();
        }
    }

    public static String findTarget(){
        String classPath = System.getProperty("java.class.path");
        String[] files = classPath.split(";");
        String targetURL = "";
        for(String file: files){
            if(file.contains("classes")){ //hardcode,please take case of this
                targetURL = file;
                break;
            }
        }
        return targetURL;
    }

}
