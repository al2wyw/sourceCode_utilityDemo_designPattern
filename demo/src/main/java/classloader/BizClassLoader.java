package classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/1/25
 * Time: 16:15
 * Desc:
 */
class BizClassLoader extends URLClassLoader {

    public BizClassLoader(URL[] urls){
        super(urls, null);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.contains("NotLoad")) {// ǿ�в����������
            System.out.println("NotLoad");
            return null;
        }
        return super.loadClass(name);
    }
}
