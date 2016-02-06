package demo;

/**
 * Created by johnny.ly on 2015/12/25.
 */
public class testSplitShuhao {
    public static void main(String[] args) {
        String target = "jintiane|test";
        String[] res = target.split("\\|");
        System.out.println(res[0]+" "+res[1]);

        String test = "##1942342134";
        String[] rest = test.split("#+");
        System.out.println(rest.length);
    }
}
