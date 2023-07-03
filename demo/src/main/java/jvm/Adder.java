package jvm;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/1/2
 * Time: 20:18
 * Desc:
 */
public class Adder {

    private int val;

    private static int count = 90000;

    public Adder(int val) {
        this.val = val / count;
        count--;
    }

    public int add(int i, int j){
        return i + j + val;
    }
}
