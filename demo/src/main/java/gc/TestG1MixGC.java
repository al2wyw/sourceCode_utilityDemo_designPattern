package gc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/1
 * Time: 19:31
 *       -XX:+UseG1GC
 *       -XX:InitiatingHeapOccupancyPercent=40 -XX:MaxGCPauseMillis=200
 *       -XX:ConcGCThreads=4 -XX:ParallelGCThreads=8
 *       -XX:G1HeapRegionSize=1m -Xms10M -Xmx10M -Xmn4M
 */
public class TestG1MixGC {

    private static final List<BigObject> LISTS = new ArrayList<>(100);

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 32; i++) {
            if ( i == 14) {
                System.out.println("gc point");
            }
            LISTS.add(new BigObject());
        }

        System.out.println("done");

        Thread.sleep(234233333L);
    }
}