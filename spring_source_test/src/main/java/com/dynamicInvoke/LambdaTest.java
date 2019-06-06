package com.dynamicInvoke;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/5/26
 * Time: 0:21
 * Desc:
 */
public class LambdaTest {

    public static void print(String s){
        System.out.println(s);
    }

    public static void main( String args[] ) throws Throwable{
        String local = "test";
        List<String> test = Lists.newArrayList("Str","test","access");
        test.stream()
                .filter(str -> str.contains(local))
                .forEach(str -> Stream.of(str.split("e"))
                                .filter(s -> s.contains("t"))
                                .peek(LambdaTest::print)
                                .forEach(s -> System.out.println(s))
                );
    }

    public static void test(){
        List<String> test = Lists.newArrayList("test1","test2","test3");
        test.stream()
                .filter(str -> str.startsWith("test"))
                .forEach(System.out::println);
    }
}
