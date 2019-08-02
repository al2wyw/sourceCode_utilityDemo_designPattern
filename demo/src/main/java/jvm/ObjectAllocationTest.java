package jvm;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/8/2
 * Time: 12:42
 * Desc: 逃逸分析 -> 栈上分配
 *      TLAB -> 线程专属堆快分配
 *      -XX:+DoEscapeAnalysis
 *      -XX:+PrintEscapeAnalysis
 *
 *      -XX:+UseTLAB
 *      -XX:+PrintTLAB
 *      -XX:TLABSize
 *
 *      -server -Xmx10m -Xms10m -XX:+DoEscapeAnalysis -XX:+PrintGC
 */
public class ObjectAllocationTest {

    public static void alloc() {
        byte[] b = new byte[2];
        b[0] = 1;
    }

    public static void main(String[] args) {
        long b = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        long e = System.currentTimeMillis();
        System.out.println(e - b);
    }

}
