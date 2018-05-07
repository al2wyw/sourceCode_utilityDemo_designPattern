package classloader;

import java.io.File;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/1/25
 * Time: 16:05
 * Desc:
 */
public class ClassLoadError {
    /**
     * ClassNotFoundException �������ʱ���Ҳ���class�ļ���һ����classpath���ò��ԣ�������ȱ������
     * NoClassDefFoundError ���Ǽ������ʱ�򱨵Ĵ��󣬶����ڳ�ʼ����ʱ�򱨵Ĵ��� ����ҪѰ��һ�����Class�����ʱ���ڵ�ǰ�����ռ��Ҳ������Class������ô���׳��������
     * ClassCastException ����ǿ��ת����ʱ��Ŀ��Class�Ѿ��ҵ��ˣ���������Ҫ��ת�Ķ��������ز�һ��������ת������
     * */
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
