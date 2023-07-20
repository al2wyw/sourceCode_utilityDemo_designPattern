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

    private static final int MAX_LOOP = 100;

    private static ThreadLocal<Stack<LabelLoop>> loopCount = ThreadLocal.withInitial(Stack::new);

    public static void checkLoop(int loop) {
        if (loop > MAX_LOOP) {
            throw new RuntimeException("loop exceeds max " + MAX_LOOP);
        }
        //待解决问题: 直接抛异常导致资源泄露
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
        if (labelLoop.loop > MAX_LOOP) {
            label2loop.clear();
            throw new RuntimeException("loop exceeds max " + MAX_LOOP);
        }
        //待解决问题:
        //没有嵌套的loop没有清理stack
        //异常退出时没有清理stack
    }

    public static class LabelLoop {
        public String label;
        public int loop;
    }
}
