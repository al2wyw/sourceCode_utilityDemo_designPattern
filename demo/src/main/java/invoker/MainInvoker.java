package invoker;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.Descriptor;
import org.apache.poi.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/2
 * Time: 20:45
 * Desc: can not define classes start with java: Prohibited package name (SecurityException)
 */
public class MainInvoker {

    private static Method defineClass0;

    static
    {
        try
        {
            Method field = ClassLoader.class.getDeclaredMethod("defineClass0",
                    String.class,byte[].class, int.class, int.class, ProtectionDomain.class);
            field.setAccessible(true);
            defineClass0 = field;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String className = args[0];

        URLClassLoader appLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
        URL[] urls = appLoader.getURLs();
        TransformClassLoader loader = new TransformClassLoader(urls, null);
        Class klass = loader.loadClass(className);
        Method method = klass.getDeclaredMethod("main", String[].class);
        method.invoke(null, (Object) null);
    }

    private static class TransformClassLoader extends URLClassLoader {

        private ClassPool cp = ClassPool.getDefault();

        private TransformClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            if(name.equals("java.nio.ByteBuffer")) {
                String path = name.replace('.', '/').concat(".class");
                try {
                    InputStream input = super.getResourceAsStream(path);

                    if (input == null) {
                        return super.findClass(name);
                    }
                    byte[] newContent = IOUtils.toByteArray(input);
                    IOUtils.closeQuietly(input);
                    return (Class)defineClass0.invoke(this,name, newContent, 0, newContent.length,
                            this.getClass().getProtectionDomain());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return super.loadClass(name,resolve);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if(name.equals("sun.misc.Cleaner")) {
                String path = name.replace('.', '/').concat(".class");
                try {
                    InputStream input = super.getResourceAsStream(path);

                    if (input == null) {
                        return super.findClass(name);
                    }
                    byte[] content = IOUtils.toByteArray(input);
                    IOUtils.closeQuietly(input);

                    ByteArrayInputStream bin = new ByteArrayInputStream(content);
                    CtClass ctClass = cp.makeClass(bin);
                    CtMethod ctMethod = ctClass.getMethod("clean", Descriptor.ofMethod(CtClass.voidType, null));
                    ctMethod.insertBefore("System.out.println(\"clean invoke\");");

                    byte[] newContent = ctClass.toBytecode();
                    return (Class)defineClass0.invoke(this,name, newContent, 0, newContent.length,
                            this.getClass().getProtectionDomain());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return super.findClass(name);
        }
    }
}