package netty;

import io.netty.util.ResourceLeak;
import io.netty.util.ResourceLeakDetector;

import java.io.FileInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/18
 * Time: 22:22
 * Desc:
 */
public class ResourceLeakDetectorTest {

    private static ResourceLeakDetector<FileInputStream> detector = new ResourceLeakDetector<>(FileInputStream.class);

    public static void main(String args[]) throws Exception{
        test();
        System.gc();
        Thread.sleep(10000);
    }

    private static void test() throws Exception{
        String basePath = ResourceLeakDetectorTest.class.getClassLoader().getResource("").getPath();
        FileInputStream fin = new FileInputStream(basePath + "gc/TestCMS.class");
        ResourceLeak leak = detector.open(fin);
        //fin.close();
        //leak.close();
    }
}