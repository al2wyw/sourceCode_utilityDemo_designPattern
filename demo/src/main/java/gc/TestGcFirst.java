package gc;

import sun.jvm.hotspot.runtime.VM;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/1
 * Time: 19:31
 * Desc:  跟jvm底层相关的demo 不要用idea来跑，idea会挂载idea_rt.jar影响demo !!!
 *       -XX:+UseG1GC
 *       -XX:InitiatingHeapOccupancyPercent=50 -XX:MaxGCPauseMillis=200
 *       -XX:ConcGCThreads=4 -XX:ParallelGCThreads=8
 *       -XX:G1HeapRegionSize=1m -Xms10M -Xmx10M -Xmn4M
 *       -XX:+UnlockDiagnosticVMOptions -XX:+G1PrintHeapRegions
 *       -XX:+UnlockExperimentalVMOptions -XX:G1LogLevel=finest
 *       -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 *       -XX:G1NewSizePercent=40 -XX:G1MaxNewSizePercent=40
 */
public class TestGcFirst {

    private static final int KB = 1024;

    private static final int ARRAY_PAYOFF = 8 + 4 + 4; // 64位机器指针大小 + UseCompressedOops + array len

    public static void main(String[] args) throws Exception {
        Object o = new Object();
        for (int i = 0; i < 32; i++) {
            if ( i == 14) {
                System.out.println("gc point");
            }
            byte[] KB100 = new byte[256 * KB - ARRAY_PAYOFF];
            System.out.println(Long.toHexString(TestFullGC.addressOf(KB100)));
        }

        System.out.println("done");

        Thread.sleep(234233333L);
    }

    private static void testBigObject() throws Exception {
        Object o = new Object();
        for (int i = 0; i < 32; i++) {
            if ( i == 14) {
                System.out.println("gc point");
            }
            BigObject bigObject = new BigObject();
            System.out.println(Long.toHexString(TestFullGC.addressOf(bigObject)));
        }

        System.out.println("done");

        Thread.sleep(234233333L);
    }
}