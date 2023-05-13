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

    public Adder(int val) {
        this.val = val;
    }

    public int add(int i, int j){
        return i + j + val;
    }
}
