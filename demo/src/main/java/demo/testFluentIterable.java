package demo;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by johnny.ly on 2016/4/7.
 */
public class testFluentIterable {
    public static void main(String[] args){
        List<StringBuilder> res = FluentIterable.from(Arrays.asList(new StringBuilder("test1"), new StringBuilder("test2"), new StringBuilder("test3"), new StringBuilder("start1"))).filter(
                new Predicate<StringBuilder>() {
                    public boolean apply(StringBuilder input) {
                        if (input.toString().startsWith("test")) {
                            return true;
                        }
                        return false;
                    }
                }
        ).toList();//Immutable list
        System.out.println(res.get(0));
        res.get(0).append("t");
        System.out.println(res.get(0));
    }
}
