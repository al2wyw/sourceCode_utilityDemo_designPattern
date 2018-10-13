package classloader;

import java.lang.String;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/5/15
 * Time: 15:43
 * Desc:
 */
public class StaticLoadTest {

    static {
        System.out.println("StaticLoadTest init");
    }
    public static void main(String[] args) {
        int i = Test.A;
        i = Test.B;
        i = Test.C;
        int j = 10;
        int k = 11;
        int h = 12;
        int f = j + k + h;
    }
}
