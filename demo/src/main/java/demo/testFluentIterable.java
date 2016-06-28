package demo;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;

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

        List<Integer> test = FluentIterable.from(Arrays.asList(new StringBuilder("test1"), new StringBuilder("test2"), new StringBuilder("test3"), new StringBuilder("start1")))
                .transform(new Function<StringBuilder, Integer>() {
                    public Integer apply(StringBuilder input) {
                        return Integer.valueOf(input.toString().substring(input.length() - 1, input.length()));
                    }
                })
                .toList();
        System.out.println(test.size());
        Iterable<Integer> filteredList = Iterables.filter(test, new Predicate<Integer>() {
            public boolean apply(Integer input) {
                System.out.println("filter out:" + input);//没有"Iterable<Integer> filteredList ="不能打印,竟然是异步调用apply
                return true;
            }
        });
        for(int i:filteredList){
            System.out.println("test:" + i);
        }
    }
}
