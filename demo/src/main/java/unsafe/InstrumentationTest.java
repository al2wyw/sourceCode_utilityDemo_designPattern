package unsafe;

import bootstrap.ThreadLocals;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.bytecode.Descriptor;
import org.apache.poi.util.IOUtils;
import sun.misc.Cleaner;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.ByteBuffer;
import java.security.ProtectionDomain;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/11
 * Time: 22:27
 * Desc:    -Xbootclasspath/a:D:\springboot\sourceCode_utilityDemo_designPattern\demo\bootstrap\target\bootstrap-1.0-SNAPSHOT.jar
             -XX:MaxDirectMemorySize=50M
             -XX:+PrintGCDetails
             -XX:+PrintGCTimeStamps
             -javaagent:agent.jar
 *          -Xbootclasspath:   完全取代基本核心的Java class 搜索路径.不常用,否则要重新写所有Java 核心class
 *          -Xbootclasspath/a: 后缀在核心class搜索路径后面.常用!!
 *          -Xbootclasspath/p: 前缀在核心class搜索路径前面.不常用,避免引起不必要的冲突.
 *
 *          can re-transform classes start with java !!!
 *          很多class在加载Instrumentation前就load完了，无法对其进行transform
 */
public class InstrumentationTest {

    public static void main(final String[] args) throws Exception {
        ClassFileTransformer transformer = new MyTransformer();

        InstrumentationSteal.instrumentation.addTransformer(transformer, true);
        InstrumentationSteal.instrumentation.retransformClasses(Cleaner.class);// 不能新增删除字段

        for(int i=0; i<100;i++) {
            ThreadLocals.name.set("test " + i);
            ByteBuffer db = ByteBuffer.allocateDirect(1024 * 1024);
            db.put((byte) 1);
        }
    }

    private static class MyTransformer implements ClassFileTransformer{

        private static ClassPool cp = ClassPool.getDefault();

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            String name = className.replace("/",".");
            if(name.equals("sun.misc.Cleaner")) {
                try {
                    System.out.println(loader);
                    ByteArrayInputStream bin = new ByteArrayInputStream(classfileBuffer);

                    CtClass ctClass = cp.makeClass(bin);

                    CtConstructor ctConstructor = ctClass.getDeclaredConstructor(new CtClass[]{cp.get("java.lang.Object"), cp.get("java.lang.Runnable")});
                    ctConstructor.setBody("{" +
                            "super($1, this.dummyQueue); " +
                            "String name = bootstrap.ThreadLocals.name.get();" +
                            "this.thunk = new bootstrap.MyCleanerRunnable(name,$2);" +
                            "}");

                    CtMethod ctMethod = ctClass.getMethod("clean", Descriptor.ofMethod(CtClass.voidType, null));
                    ctMethod.insertBefore("System.out.println(\"clean invoke: \");");

                    byte[] newContent = ctClass.toBytecode();
                    IOUtils.closeQuietly(bin);
                    ctClass.defrost();
                    return newContent;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return classfileBuffer;
        }
    }
}