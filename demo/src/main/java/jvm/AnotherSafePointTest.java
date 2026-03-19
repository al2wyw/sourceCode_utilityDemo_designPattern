package jvm;

/**
 * uncommon trap的退优化不会触发stw，但如果其他线程发起stw它会等到stw后才继续
 * -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=compileonly,*AnotherSafePointTest.* -XX:CompileCommand=compileonly,*AtomicInteger.* -XX:CompileCommand=compileonly,*Unsafe.*
 * -XX:-BackgroundCompilation -XX:CompileThreshold=1000
 * -XX:GuaranteedSafepointInterval=100 -XX:+SafepointALot
 * jvm.AnotherSafePointTest 9999990 9999991
 * */
import java.util.concurrent.atomic.AtomicInteger;

public class AnotherSafePointTest {

    public static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 500000000; i++) {
                num.getAndAdd(1);
            }
        };
        Thread t1 = new Thread(runnable, "ThreadOne");
        Thread t2 = new Thread(runnable, "ThreadTwo");
        t1.start();
        t2.start();
        long start = System.currentTimeMillis();
        System.out.println("***********主线程开始睡眠***********");
        try {
            run(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("***********主线程恢复,耗时：+" + (end - start) + "***********");
        }
    }

    public static void run(int count, int loop) {
        int ret = 0xabc;
        for (int i = 0; i < loop; i++) {
            ret = ret / count;
            count--;
        }

        System.out.println(ret);
    }
}
