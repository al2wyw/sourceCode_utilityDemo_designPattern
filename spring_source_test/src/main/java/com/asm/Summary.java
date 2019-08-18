package com.asm;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/8/16
 * Time: 17:43
 * Desc:
 *  ASM (
 *      可以拦截最底层的方法调用指令;
 *      增加 annotation 和generic signature比较简单(用SignatureWriter生成sign string比较复杂)
 *      )
 *  Cglib extends ASM (封装了asm的api接口，更易于操作，是asm的升级版)
    ByteBuddy depends ASM (接口风格与asm相差甚远)

    Javassist (
                支持直接代码字符编译;
                annotation 和generic signature的增添比较直观(用SignatureAttribute添加sign比较复杂)
              )
 */
public class Summary {
}
