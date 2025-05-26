package com.asm;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/5/28
 * Time: 20:08
 * Desc: code for inspecting bool jump instruction
 */
public class InstructionsBoolJump {

    private int x;

    private int y;

    public InstructionsBoolJump(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void main(final String[] args) throws Exception {
        InstructionsBoolJump t = new InstructionsBoolJump(20, 200);
        boolean flag = t.x > 10 || (t.y > 20 && t.x < 10);
        System.out.println(flag);
    }
}
