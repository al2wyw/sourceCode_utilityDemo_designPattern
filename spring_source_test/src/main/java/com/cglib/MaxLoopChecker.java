package com.cglib;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: liyang
 * Date: 2023-07-18
 * Time: 14:48
 * Description:
 */
public class MaxLoopChecker {

    private static int maxLoop = 10;

    private static ThreadLocal<Stack<LabelLoop>> loopCount = ThreadLocal.withInitial(Stack::new);

    public static void checkLoop(int loop) {
        if (loop > maxLoop) {
            throw new RuntimeException("loop exceeds max " + maxLoop);
        }
    }

    public static void checkLoop(String label) {
        Stack<LabelLoop> label2loop = loopCount.get();
        if (label2loop.size() == 0) {
            LabelLoop labelLoop = new LabelLoop();
            labelLoop.label = label;
            label2loop.push(labelLoop);
            return;
        }

        //要求在循环开始时调用checkLoop
        LabelLoop labelLoop = label2loop.peek();
        if (labelLoop.label.equals(label)) {
            labelLoop.loop++;
        } else if (label2loop.size() >= 2 && label2loop.elementAt(label2loop.size() - 2).label.equals(label)) {
            label2loop.pop();
            labelLoop = label2loop.peek();
            labelLoop.loop++;
        } else {
            labelLoop = new LabelLoop();
            labelLoop.label = label;
            label2loop.push(labelLoop);
        }
        if (labelLoop.loop > maxLoop) {
            label2loop.clear();
            throw new RuntimeException("loop exceeds max " + maxLoop);
        }
        //没有嵌套的loop没有清理
        //异常退出时没有清理
    }

    public static class LabelLoop {
        public String label;
        public int loop;
    }
}
