package gc;

import utils.ThreadUtils;

/**
 *
 * -Xloggc:./gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xms100M -Xmx100M -Xmn10m -XX:SurvivorRatio=8
 * new gen 的大小与young gc时长无关
* */
public class TestYoungGC {

    private static final int KB = 1024;

    public static void main(String[] args) throws Exception {
        for (int l = 0; l < 10; l++) {
            for (int i = 0; i < 1024; i++) {
                byte[] b = alloc(1 * KB);
                System.out.println(b[0]);
            }
            ThreadUtils.sleep(1000);
        }
    }

    public static byte[] alloc(int size) {
        byte[] b = new byte[size];
        b[0] = 1;
        return b;
    }
}
