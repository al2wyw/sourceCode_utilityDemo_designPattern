package netty;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/18
 * Time: 13:51
 * Desc: 与最小堆实现相比: 增加和删除任务时间复杂度为O(1)
 *       单线程worker，bucket是双向链表，timeouts和cancelledTimeouts都是mpsc queue
 *       processCancelledTasks: 执行cancelledTimeouts里面的任务 (Timeout调用cancel时只是enqueue, 这里可能会有内存压力)
 *       transferTimeoutsToBuckets: 从timeouts取出任务，加入到对应的bucket (HashedWheelTimer调用new时只是enqueue)
 *       bucket.expireTimeouts(deadline): 真正执行timeoutTask，同时删除执行完的timeoutTask
 *       bucket作为非线程安全的双向链表，保证单线程操作，而timeouts和cancelledTimeouts可以多线程并发操作
 *
 *       waitForNextTick处理精确时间 及优雅退出 !!! (可以使用delayQueue对bucket排序避免长时间空转Kafka)
 *       stop优雅处理未完成任务
 *       used as tombstone: MpscLinkedQueue的第一个元素是个空元素，只作为标识符(哨兵)
 *       MpscLinkedQueue: headRef给consumer使用(单线程独占)，tailRef给producer使用(多线程竞争)
 *                        两个ref之间使用long类型的padding隔离, 元素之间的next使用lazySet(不需要线程可见)(next是否不需要volatile???)
 */
public class HashedWheelTimerTest {

    public static void main(String args[]) throws Exception{
        HashedWheelTimer timer = new HashedWheelTimer(100, TimeUnit.MILLISECONDS);
        timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("test is run");
                timer.newTimeout(this, 3, TimeUnit.SECONDS);
            }
        },3,TimeUnit.SECONDS);

        int leak = Runtime.getRuntime().availableProcessors() * 4;
        System.out.println(leak);
        for(int i=0; i<leak+1; i++){ //io.netty.util.ResourceLeakDetector reportLeak
            new HashedWheelTimer(100, TimeUnit.MILLISECONDS);
        }
    }
}