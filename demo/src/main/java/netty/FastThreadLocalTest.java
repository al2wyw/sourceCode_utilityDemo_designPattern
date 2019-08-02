package netty;

import io.netty.util.concurrent.FastThreadLocal;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/7/19
 * Time: 17:35
 * Desc:
 *      ThreadLocal使用 hash和array 在hash冲突时采用线性探测(通常是链址)来存储变量 (代码复杂,不像想象中简单)
 *      FastThreadLocal 使用 index和array 来存储变量
 */
public class FastThreadLocalTest {

    private static FastThreadLocal<String> ID = new FastThreadLocal<>();

    public static void main(String args[]) throws Exception{
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,20,10, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new NamedThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
        executor.submit(() -> {
            ID.set("testId");
            test();
        });
        executor.awaitTermination(3,TimeUnit.SECONDS);
    }

    private static void test(){
        System.out.println("get from local thread: " + ID.get());
    }

}
