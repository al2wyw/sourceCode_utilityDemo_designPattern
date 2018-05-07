package classloader;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.locks.LockSupport;

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
        InputStream in = cl.getResourceAsStream("./classloader/Main.class");
        byte[] code = new byte[1024*128];
        int len = in.read(code);
        byte[] byteCodes = Arrays.copyOf(code,len);
        MyClassLoader classLoader = new MyClassLoader(cl);
        Class klass = classLoader.defineClass("classloader.Main",byteCodes);
        Object c = klass.newInstance();
        //classLoader.defineClass("com.javac.Main",byteCodes);//error
        LockSupport.parkNanos(10000000000L);
        System.out.println("wake up...");
        classLoader = null;
        LockSupport.parkNanos(10000000000L);
        MyClassLoader classLoader1 = new MyClassLoader(cl);
        Class mainclass = classLoader1.defineClass("classloader.Main",byteCodes);
        Object main = mainclass.newInstance();
        Method hello = mainclass.getMethod("hello", null);
        hello.invoke(main,null);
    }

    public void hello(){
        System.out.println("hello");
    }
}
