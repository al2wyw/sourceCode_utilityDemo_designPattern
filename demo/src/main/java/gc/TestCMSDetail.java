package gc;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/1
 * Time: 19:31
 * Desc: -XX:+UseConcMarkSweepGC
 *       -XX:CMSInitiatingOccupancyFraction=70 -XX:+UseCMSInitiatingOccupancyOnly
 *       -Xms11M -Xmx11M -Xmn1m -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1
 *       -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC
 */
public class TestCMSDetail {

    private static final int KB = 1024;

    public static void main(String[] args) throws Exception{
        Object o = new Object();
        byte[] KB100 = new byte[200 * KB];
        byte[] KB101 = new byte[200 * KB];

        // gc point:
        byte[] KB102 = new byte[200 * KB];
        byte[] KB103 = new byte[200 * KB];

        System.out.println("done");
    }
}