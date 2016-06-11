package utils;

/**
 * Created by johnny.ly on 2016/5/9.
 */
public class ThreadUtils {
    public static void printThreadName(String direct, String mess){
        System.out.println(direct+"---------------"+Thread.currentThread().getName()+"--------------"+mess);
    }

    public static void sleep(long test){
        try{
            Thread.sleep(test);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
