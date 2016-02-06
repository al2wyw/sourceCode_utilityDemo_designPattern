package demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnny.ly on 2015/10/3.
 */
public class IntegerArrayList {
    public static void main(String[] args) {
        List<Integer> test = new ArrayList<Integer>();
        test.add(new Integer(10));
        test.add(new Integer(10));
        test.add(new Integer(10));

        for(Integer i : test){
            i += 10;
            System.out.println(i);

        }
        System.out.println(test.get(0));
        Integer ttt = null;
        int t = ttt;
        System.out.println(t);
    }
}
