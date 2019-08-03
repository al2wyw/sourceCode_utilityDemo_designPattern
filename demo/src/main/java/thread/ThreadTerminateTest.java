package thread;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/10/29
 * Time: 12:30
 * Desc:
 *      thread sleep/wait/join -> thread.interrupt() throw InterruptException but thread.interrupted is false
 *      thread park -> thread.interrupt()
 *      socket read -> socket.close()
 *      selector select -> selector.wakeup/close(call wakeup)/thread.interrupt(set interruptible to call wakeup)
 *      statement -> statement.cancel() 需要数据库支持
 *      channel read -> thread.interrupt() (set interruptible to implCloseChannel and finally throw ClosedByInterruptException)
 *      总的来讲interrupt时会抛异常的同时也会把interrupted标志清除, 不抛异常的不会清除interrupted标志
 * best practice: 1. 不要提交不可中断的任务到线程池
 *                2. 线程持有锁时不做阻塞操作 (拆分逻辑，把阻塞操作移到临界区间外)
 */
public class ThreadTerminateTest {
    public static void main(String args[]) throws Exception{
        Thread t = new Thread(() -> {
            Thread curr = Thread.currentThread();
            try{
                System.out.println("thread isInterrupted=" + curr.isInterrupted());
                Thread.sleep(3000);
            }catch (InterruptedException e){
                System.out.println("thread isInterrupted=" + curr.isInterrupted());//thread isInterrupted=false
            }
        });
        t.start();
        System.out.println("main to interrupt");
        t.interrupt();
        //t.start();//先interrupt再启动线程 thread isInterrupted=false
        t.join();
    }
}
