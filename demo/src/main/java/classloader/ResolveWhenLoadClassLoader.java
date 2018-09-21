package classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/9/21
 * Time: 14:44
 * Desc:
 */
public class ResolveWhenLoadClassLoader extends URLClassLoader {

    public ResolveWhenLoadClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.println("ResolveWhenLoadClassLoader name="+name);
        return loadClass(name, true);
    }
}
