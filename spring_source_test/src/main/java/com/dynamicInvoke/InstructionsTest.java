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
        while(i<10 && j > 10){
            System.out.println(i++);
        }
        long l = Long.valueOf(args[3]);
        i = l != 10L ? 2 : 1;
        System.out.println(i);
    }
}
