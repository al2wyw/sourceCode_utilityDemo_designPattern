package gc;

import sun.misc.VM;

import java.nio.ByteBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/1
 * Time: 19:31
 * Desc: -XX:+UseConcMarkSweepGC
 *       -XX:CMSInitiatingOccupancyFraction=70 -XX:+UseCMSInitiatingOccupancyOnly
 *       -XX:+ExplicitGCInvokesConcurrent/-XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses System.gc -> ParNew + CMS
 *       -XX:+CMSClassUnloadingEnabled
 *       -XX:CMSMaxAbortablePrecleanTime=5000
 *       -XX:MaxDirectMemorySize=50M  -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 */
public class TestCMS {

    public static void main(String[] args) throws Exception{
        System.out.println(Runtime.getRuntime().maxMemory()/(1024*1024) + " mb");
        System.out.println(VM.maxDirectMemory()/(1024*1024) + " mb");
        for(int i=0; i<100;i++) {
            ByteBuffer db = ByteBuffer.allocateDirect(1024 * 1024);
            db.put((byte) 1);
        }

    }
}