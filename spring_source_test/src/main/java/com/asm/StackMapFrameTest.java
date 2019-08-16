package com.asm;

import com.annotation.TargetMethod;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/7/7
 * Time: 18:29
 * Desc: check bytecode outline to see frame
 *       visitFrame -> the first arg ???
 *       compute_frame -> getCommonSuperClass ???
 *       stackMap Frame: 执行某一特定字节码前，每个局部变量槽和每个操作数槽包含的值的类型(分为compressed和expanded)
 *       初始帧由参数构成，每个跳转指令后才要重新计算帧，此时和前一帧(比如初始帧)作比较 （实际例子好像不是这样子的 ???）
 *       failed !!!
 */
public class StackMapFrameTest {
    @TargetMethod
    private <T> void test(List<T> args){
        return;
    }

    public static void main( String args[] ) {

        String i = "test";
        if(i.contains("t1")){
            int m = 10;
            if(i.contains("t12")){
                int h = 10;
            }
            int n = 11;
        }

        if(i.contains("t2")){
            int m = 10;
        }

        if(i.contains("t3")){
            int m = 10;
        }

        String o = "111";
        System.out.println(o);
    }
}