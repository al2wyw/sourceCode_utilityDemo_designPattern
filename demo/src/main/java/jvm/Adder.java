package jvm;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/1/2
 * Time: 20:18
 * Desc:
 */
public class Adder {

    public int addOut(int i, int j) {
        return add(i, j);
    }

    private int add(int i, int j){
        return i + j;
    }
}
