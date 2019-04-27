package gc;

import javassist.ClassPool;
import javassist.CtClass;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/4/27
 * Time: 22:40
 * Desc: metaspace 在full gc后 明显减少
 */
public class ClassLoaderReCycleEnhancedTest {

    public static void main( String args[] ) throws Exception{
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.getCtClass("demoObject.BigClass");

        MyClassLoader cl = new MyClassLoader(Thread.currentThread().getContextClassLoader());
        for(int i = 0; i< 1000; i++) {
            String name = "demoObject.BigClass" + i;
            ctClass.setName(name);
            cl.defineClass(name, ctClass.toBytecode());
            ctClass.defrost();
        }

        System.out.println("wake up...");
        cl = null;
        System.gc();
        LockSupport.parkNanos(10000000000L);
        System.gc();
        LockSupport.parkNanos(10000000000L);
    }
}
