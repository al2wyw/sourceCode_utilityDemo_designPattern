package classloader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/9/21
 * Time: 14:47
 * Desc: 没有resolve ???
 */
public class ResolveTest {

    public static void main(String[] args) throws Exception {
        //Class.forName("classloader.StaticLoadTest");//will initialize the class by default
        ClassLoader cl = new ResolveWhenLoadClassLoader(new URL[]{new File(findTarget()).toURI().toURL()}, null);
        testClassLoad(cl);
        testClassLoad(Thread.currentThread().getContextClassLoader());
    }

    private static void testClassLoad(ClassLoader cl) throws Exception{
        System.out.println("testClassLoad start");
        Class staticClass = cl.loadClass("classloader.StaticLoadTest");
        System.out.println(staticClass.getName());
        System.out.println(staticClass.getClassLoader());
        staticClass.newInstance();
        System.out.println("testClassLoad end");
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
