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

/**
 *  ffe00000 -> ffe40000 地址打印的代码也会new占用内存, 分配到哪里了 ???
 *  G1HR COMMIT [0x00000000ff600000,0x00000000ff700000]
 *  G1HR COMMIT [0x00000000ff700000,0x00000000ff800000]
 *  G1HR COMMIT [0x00000000ff800000,0x00000000ff900000]
 *  G1HR COMMIT [0x00000000ff900000,0x00000000ffa00000]
 *  G1HR COMMIT [0x00000000ffa00000,0x00000000ffb00000]
 *  G1HR COMMIT [0x00000000ffb00000,0x00000000ffc00000]
 *  G1HR COMMIT [0x00000000ffc00000,0x00000000ffd00000]
 *  G1HR COMMIT [0x00000000ffd00000,0x00000000ffe00000]
 *  G1HR COMMIT [0x00000000ffe00000,0x00000000fff00000]
 *  G1HR COMMIT [0x00000000fff00000,0x0000000100000000]
 *  G1HR ALLOC(Eden) 0x00000000fff00000
 * fff6a7d0
 * fffbef90
 *  G1HR ALLOC(Eden) 0x00000000ffe00000
 * ffe00000
 * ffe40000
 * ffe80000
 * ffec0000
 *  G1HR ALLOC(Eden) 0x00000000ffd00000
 * ffd00000
 * ffd40000
 * ffd80000
 * ffdc0000
 *  G1HR ALLOC(Eden) 0x00000000ffc00000
 * ffc00000
 * ffc40000
 * ffc80000
 * ffcc0000
 * 0.044: [GC pause (G1 Evacuation Pause) (young) G1HR #StartGC 1
 *  G1HR ALLOC(Survivor) 0x00000000ffb00000
 *  G1HR RETIRE 0x00000000ffb00000 0x00000000ffb67030
 *  G1HR #EndGC 1
 * , 0.0019819 secs]
 * */