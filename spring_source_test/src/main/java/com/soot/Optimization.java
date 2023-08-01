package com.soot;

/**
 * Created with IntelliJ IDEA.
 * User: liyang
 * Date: 2023-07-30
 * Time: 20:12
 * Description: javac 会进行常量折叠, 部分复杂的数学表达式没有进行常量折叠 ???
 */
public class Optimization {

    public void copyPropagation() {
        int a = 10;
        int b = a + 1;
        System.out.println(b);
    }

    public void copyPropagation2() {
        int a = 10;
        for (int i = 0; i < 10; i++) {
            System.out.println(a);
        }
        int b = 11;
        for (int i = 0; i < 10; i++) {
            System.out.println(b);
        }
    }

    public void copyPropagation3() {
        int a = 10;
        for (int i = 0; i < 10; i++) {
            System.out.println(a++);
        }
        int b = 11;
        for (int i = 0; i < 10; i++) {
            System.out.println(b++);
        }
    }

    public static void main(String[] args) {

    }
}
