package classloader;

import org.apache.cxf.helpers.FileUtils;
import org.apache.cxf.helpers.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/10/30
 * Time: 14:07
 * Desc:
 */
public class LoadWithNoPDTest {
    public static void main(String[] args) throws Exception{
        MyLoader loader = new MyLoader();
        InputStream inputStream = LoadWithNoPDTest.class.getResourceAsStream("StaticLoadTest.class");
        byte[] buffer = new byte[1024*128];
        int i = 0;
        int len = 0;
        while((i = inputStream.read(buffer,len,1024)) != -1){
            len  += i;
        }
        inputStream.close();
        Class klass = loader.defineClass("classloader.StaticLoadTest", buffer, len);//defaultDomain is used
        System.out.println(klass.getClassLoader());
        Class temp = loader.findLoadClass("classloader.StaticLoadTest");
        System.out.println(temp != null);
        temp = loader.findLoadClass("classloader.Test");
        System.out.println(temp != null);

        //reference resolve, initiate the loading of Test class
        Method method = klass.getDeclaredMethod("main",String[].class);
        method.invoke(null,(Object)null);

        temp = loader.findLoadClass("classloader.Test");
        System.out.println(temp != null);
    }

    private static class MyLoader extends ClassLoader{

        public Class<?> defineClass(String name, byte[] b, int len){
            return super.defineClass(name,b,0,len);
        }

        public Class<?> findLoadClass(String name){
            return super.findLoadedClass(name);
        }
    }
}
