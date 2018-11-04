package classLoaderPath;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/1/18
 * Time: 16:25
 * Desc:
 */
public class Main {
    public static void main(String[] args) throws Exception{
        List<URL> urlList = new ArrayList<>();
        //由于Main是在classes/目录下面，所以getResource返回的URL是一个FileURL
        urlList.add(Main.class.getResource("/"));
        urlList.add(Main.class.getResource(""));
        urlList.add(Main.class.getClassLoader().getResource("")); // l3 equal to l1
        urlList.add(Main.class.getClassLoader().getResource("/")); // null

        urlList.add(Main.class.getClassLoader().getResource("./"));// "./" equal to ""
        urlList.forEach(System.out::println);

        System.out.println("MainClass getClassLoader: " + Main.class.getClassLoader());
        System.out.println("MainClass getContextClassLoader: " + Thread.currentThread().getContextClassLoader());


        URL[] urls = new URL[1];
        urls[0] = new URL("file:/D:/non-java-standard-1.0-SNAPSHOT.jar");//new URL("jar:file:/D:/non-java-standard-1.0-SNAPSHOT.jar!/");// !/ is needed
        URLClassLoader urlClassLoader = new URLClassLoader(urls, null);
        Class<?> clazz = urlClassLoader.loadClass("demo.decimal_parse_test");
        System.out.println(clazz.newInstance());

        System.out.println("InnerThread1 getClassLoader: " + clazz.getClassLoader());
        urlList = new ArrayList<>();
        urlList.add(clazz.getResource("/")); // null
        urlList.add(clazz.getResource(""));
        urlList.add(clazz.getClassLoader().getResource("")); // null
        urlList.add(clazz.getClassLoader().getResource("/")); // null
        urlList.add(clazz.getClassLoader().getResource("./"));// null
        urlList.add(clazz.getClassLoader().getResource("reflect/name"));
        urlList.forEach(System.out::println);

        URL test = new URL("jar:file:/D:/non-java-standard-1.0-SNAPSHOT.jar!/reflect/proxy");
        URL test1 = new URL(test,"/reflect/name");
        System.out.println(test1.getFile());
        test1.openConnection();
        URL test2 = new URL(test,"reflect/name");//JarHandler parseContextSpec 的第二个if有问题
        System.out.println(test2.getFile());// file:/D:/non-java-standard-1.0-SNAPSHOT.jar!/reflect/reflect/name
    }
}