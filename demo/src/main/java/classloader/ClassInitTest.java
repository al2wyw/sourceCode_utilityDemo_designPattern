package classloader;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/19
 * Time: 16:28
 * Desc: class init only once
 */
public class ClassInitTest {

    public static void main(String[] args) {
        try{
            BadClass.doSomething();
        }catch (Throwable e){
            e.printStackTrace();
        }

        BadClass.doSomething();
    }

    public static class BadClass{
        private static int a=100;
        static{
            System.out.println("before init");
            int b=3/0;
            System.out.println("after init");
        }

        public static void doSomething(){
            System.out.println("do somthing");
        }
    }
}