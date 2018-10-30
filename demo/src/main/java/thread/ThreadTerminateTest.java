package thread;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/10/29
 * Time: 12:30
 * Desc:
 *      thread sleep/park -> thread.interrupt()
 *      socket read -> socket.close()
 *      selector select -> selector.wakeup/close
 *      statement -> statement.cancel() 需要数据库支持
 *      channel read -> thread.interrupt() ClosedByInterruptException
 * best practice: 1. 不要提交不可中断的任务到线程池
 *                2. 线程持有锁时不做阻塞操作 (拆分逻辑，把阻塞操作移到临界区间外)
 */
public class ThreadTerminateTest {
    public static void main(String args[]) throws Exception{

    }
}
