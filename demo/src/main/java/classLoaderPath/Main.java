package classLoaderPath;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/1/18
 * Time: 16:25
 * Desc:
 */
public class Main {
    public static void main(String[] args){
        //URLClassLoader has URLClassPath(ucp) has URLClassPath.Loader has base -> "jar:file:/D:/non-java-standard-1.0-SNAPSHOT.jar!/"
        URL l1 = Main.class.getResource("/");
        URL l2 = Main.class.getResource("");
        URL l3 = Main.class.getClassLoader().getResource(""); // l3 equal to l1
        URL l4 = Main.class.getClassLoader().getResource("/"); // null
        URL l5 = Thread.currentThread().getContextClassLoader().getResource("./");// "./" equal to ""

        System.out.println("MainClass getClassLoader: " + Main.class.getClassLoader());
        System.out.println("MainClass getContextClassLoader: " + Thread.currentThread().getContextClassLoader());
        new InnerThread1().start();
    }
}

class InnerThread1 extends Thread{
    @Override
    public void run() {
        try {
            URL[] urls = new URL[1];
            urls[0] = new URL("jar:file:/D:/non-java-standard-1.0-SNAPSHOT.jar!/");
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            Class<?> clazz = urlClassLoader.loadClass("demo.decimal_parse_test");
            System.out.println(clazz.newInstance());

            System.out.println("InnerThread1 getClassLoader: " + clazz.getClassLoader() +" " + clazz.getResource("") + " " + clazz.getClassLoader().getResource(""));
            System.out.println("InnerThread1 getContextClassLoader: " + Thread.currentThread().getContextClassLoader());

            this.setContextClassLoader(urlClassLoader);//comment out will cause thread2 error

            Thread innerThread2 = new InnerThread2();
            innerThread2.start();
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

class InnerThread2 extends Thread{
    @Override
    public void run() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            classLoader.loadClass("demo.IntegerArrayList");
            System.out.println("InnerThread2 getContextClassLoader: " + Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
