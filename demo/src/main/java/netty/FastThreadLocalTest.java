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
 *      ThreadLocal使用 hash取模作为下标和array来存储Entry(key是ThreadLocal) 在hash冲突时采用线性探测(通常是链址)来找到对应下标 (代码复杂,不像想象中简单)
 *      ThreadLocalMap的Entry: Key是弱引用(如果key是强引用，当ThreadLocal是局部变量又没有remove时会造成内存泄露，weekRef可以在gc时回收内存，
 *      此时key是null，并且在get和set出现hash冲突时回收value强引用)，Value是强引用
 *      最佳实践: ThreadLocal 设置为静态变量， 使用完后调用remove
 *
 *      FastThreadLocal 使用 自增index作为下标和array来存储value，利用了数组连续性，InternalThreadLocalMap使用缓存填充???
 *      注意需要手动remove
 *      内存扩容导致空洞产生，必须设置为静态变量
 */
public class FastThreadLocalTest {

    //InternalThreadLocalMap会扩容，不存在index冲突，FastThreadLocal创建后又不使用会造成数组空洞
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
