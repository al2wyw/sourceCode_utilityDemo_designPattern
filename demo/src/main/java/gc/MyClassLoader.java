package gc;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/1/15
 * Time: 15:02
 * Desc:
 */
public class MyClassLoader extends URLClassLoader {
    public MyClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
    }

    public Class<?> defineClass(String name,byte[] classData){
        System.out.println("Class name: " + name);
        return super.defineClass(name, classData, 0, classData.length);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(this +" gc");
    }
}
