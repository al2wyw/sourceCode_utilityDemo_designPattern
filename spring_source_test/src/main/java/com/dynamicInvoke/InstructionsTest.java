package com.dynamicInvoke;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/5/28
 * Time: 20:08
 * Desc:
 */
public class InstructionsTest {

    public static void main(final String[] args) throws Exception {
        int i = Integer.valueOf(args[0]);
        boolean flag = Boolean.valueOf(args[1]);
        int j = Integer.valueOf(args[2]);
        if(i==1){
            System.out.println(i);
            if(flag){
                System.out.println(flag);
                if(j == 10){
                    System.out.println(j);
                }
            }else{
                System.out.println(flag);
            }
        }else{
            if(flag){
                System.out.println(flag);
                if(j == 10){
                    System.out.println(j);
                }
            }else{
                System.out.println(flag);
            }
        }
        System.out.println(flag);
        while(i<10 && j > 10){
            System.out.println(i++);
            if (j > 11) {
                continue;
            }
            if (i > 11) {
                continue;
            }
            System.out.println(j++);
        }
        for (int h = 0; h < 11; h++) {
            System.out.println(h);
            for (int g = 0; g < 10; g++) {
                System.out.println(g);
            }
        }
        long l = Long.valueOf(args[3]);
        i = l != 10L ? 2 : 1;
        System.out.println(i);
    }
}
