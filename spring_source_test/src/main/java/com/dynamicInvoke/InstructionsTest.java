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
            print(i);
            if(flag){
                print(flag);
                if(j == 10){
                    print(j);
                }
            }else{
                print(flag);
            }
        }else{
            if(flag){
                print(flag);
                if(j == 10){
                    print(j);
                }
            }else{
                print(flag);
            }
        }
        //print(flag);
        while(i<10 && j > 10){
            print(i++);
            if (j > 11) {
                continue;
            }
            if (i > 11) {
                continue;
            }
            print(j++);
        }
        for (int h = 0; h < 11; h++) {
            print(h);
            for (int g = 0; g < 10; g++) {
                print(g);
            }
        }
        long l = Long.valueOf(args[3]);
        i = l != 10L ? 2 : 1;
        print(i);
    }
    
    public static void print(Object o) {
        //System.out.println(o);
    }
}
