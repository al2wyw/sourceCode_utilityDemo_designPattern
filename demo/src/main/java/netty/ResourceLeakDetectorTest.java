package netty;

import io.netty.util.ResourceLeak;
import io.netty.util.ResourceLeakDetector;

import java.io.FileInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/18
 * Time: 22:22
 * Desc: -Xms10M -Xmx10M -Xmn8m -XX:SurvivorRatio=8
 */
public class ResourceLeakDetectorTest {

    private static ResourceLeakDetector<FileInputStream> detector = new ResourceLeakDetector<>(FileInputStream.class);

    public static void main(String args[]) throws Exception{
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
        ResourceLeakDetectorTest test = new ResourceLeakDetectorTest();
        test.test();
        for(int i = 0; i < 10; i++){
            byte[] gc = new byte[1024*1024];
        }
        test.test();//detector.open 的时候才去检查是否泄漏，所以要多调用一次
    }

    private void test() throws Exception{
        String basePath = ResourceLeakDetectorTest.class.getClassLoader().getResource("").getPath();
        FileInputStream fin = new FileInputStream(basePath + "gc/TestCMS.class");
        ResourceLeak leak = detector.open(fin);
        fin.close();
        //leak.close();
    }
}