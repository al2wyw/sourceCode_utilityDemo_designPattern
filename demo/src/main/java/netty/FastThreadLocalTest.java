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
