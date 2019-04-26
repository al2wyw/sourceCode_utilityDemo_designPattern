package gc;

import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.locks.LockSupport;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/1/15
 * Time: 14:57
 * Desc:
 *      1. 如果实现了finalize方法(方法体非空!!!)，在对象初始化时会生成一个Finalizer(调用Finalizer.register)对象，
 *      在gc时会被gc线程放到Finalizer的queue(此时Finalizer强引用待回收的对象，只有执行完对象的finialize方法才能被回收)，最后导致不能被一次gc回收，
 *      FinalizerThread 负责从queue中remove，并调用对象finialize方法，FinalizerThread优先级比较低，可能经过多次gc还没有执行finialize方法，容易引发fullGC
 *      2. class对象和classLoader对象互相引用，必须同时被回收。Class unload的条件确实是三个，已验证
 *      3.
 */
public class ClassLoaderReCycleTest {

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
