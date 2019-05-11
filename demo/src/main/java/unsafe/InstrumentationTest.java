package unsafe;

import javassist.ClassPool;
import javassist.CtClass;
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
 * Desc: can re-transform classes start with java !!!
 */
public class InstrumentationTest {

    public static void main(final String[] args) throws Exception {
        ClassFileTransformer transformer = new MyTransformer();

        InstrumentationSteal.instrumentation.addTransformer(transformer,true);
        InstrumentationSteal.instrumentation.retransformClasses(Cleaner.class);

        for(int i=0; i<100;i++) {
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
                    ByteArrayInputStream bin = new ByteArrayInputStream(classfileBuffer);

                    CtClass ctClass = cp.makeClass(bin);
                    CtMethod ctMethod = ctClass.getMethod("clean", Descriptor.ofMethod(CtClass.voidType, null));
                    ctMethod.insertBefore("System.out.println(\"clean invoke\");");

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