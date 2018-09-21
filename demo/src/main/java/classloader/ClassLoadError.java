package classloader;

import java.io.File;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/1/25
 * Time: 16:05
 * Desc:
 *
 * sun.misc.Launcher 只是创建系统级class loader，并不是用来运行main方法的
 */
public class ClassLoadError {
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
            //sun.misc.Launcher源码
            System.out.println(ClassLoader.getSystemClassLoader().getParent());//sun.misc.Launcher$ExtClassLoader
            System.out.println(ClassLoader.getSystemClassLoader());//sun.misc.Launcher$AppClassLoader
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
