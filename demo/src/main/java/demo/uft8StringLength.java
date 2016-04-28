package demo;

/**
 * Created by johnny.ly on 2016/3/10.
 */
public class uft8StringLength {
    public static void main(String[] args) throws Exception{
        String test = "我是谁啊？abc_123";
        System.out.println(test.length());
        System.out.println(test.getBytes().length);
        System.out.println(test.getBytes("UTF-8").length);
    }
}
