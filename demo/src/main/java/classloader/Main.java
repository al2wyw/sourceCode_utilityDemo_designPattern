package classloader;

import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.locks.LockSupport;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/1/15
 * Time: 14:57
 * Desc:
 *      1. 如果实现了finalize方法(方法体非空)，在对象初始化时会生成一个Finalizer(FRef)对象，
 *      在gc时会被gc线程放到Finalizer的queue(参考各种reference的queue)，最后导致不能被一次gc回收(至少两次gc才能回收)，
 *      FinalizerThread优先级比较低，容易引发fullGC
 *      2. class对象和classLoader对象互相引用，必须同时被回收。Class unload的条件确实是三个，已验证
 *      3.
 */
public class Main {

    public static void main( String args[] ) throws Exception{
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream in = cl.getResourceAsStream("./demoObject/BigClass.class");
        byte[] code = new byte[1024*128];
        int len = in.read(code);
        byte[] byteCodes = Arrays.copyOf(code,len);
        MyClassLoader classLoader = new MyClassLoader(cl);
        Class klass = classLoader.defineClass("demoObject.BigClass",byteCodes);
        //Object c = klass.newInstance();
        //classLoader.defineClass("com.javac.Main",byteCodes);//error
        //LockSupport.parkNanos(10000000000L);
        System.out.println("wake up...");
        classLoader = null;
        //klass=null;
        System.gc();
        LockSupport.parkNanos(10000000000L);
        System.gc();
        LockSupport.parkNanos(10000000000L);
    }
}
