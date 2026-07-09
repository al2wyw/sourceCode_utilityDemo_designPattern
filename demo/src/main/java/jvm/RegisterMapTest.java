package jvm;

import java.util.concurrent.CountDownLatch;
import utils.PrintUtils;

/**
 * -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation -XX:LogFile=./mylogfile.log -XX:+PrintAssembly -XX:+PrintStubCode -XX:CompileCommand=dontinline,*RegisterMapTest.*  -XX:CompileCommand=compileonly,*RegisterMapTest.*
 * */
public class RegisterMapTest {

    private static volatile int flag = 0;
    private static final CountDownLatch latch = new CountDownLatch(1);
    private static final int LOOP = 100000;
    private static final int GC_INTERVAL = LOOP / 10;

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            latch.countDown();
            int trigger = 0;
            while (!Thread.currentThread().isInterrupted()) {
                if (flag - trigger > 0) {
                    PrintUtils.print("gc");
                    System.gc();
                    trigger++;
                }
            }
        });
        t.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int count = 0;
        for (int i = 0; i < LOOP; i++) {
            caller();
            count++;
            if (count % GC_INTERVAL == 0) {
                PrintUtils.print("gc trigger");
                flag++;
            }
        }
        t.interrupt();
    }

    public static void caller() {
        Object local = new Object();
        Object param1 = new Object();
        Object param2 = new Object();
        callee(param1, param2);//local saved on stack not in registers
        System.out.println(local);
    }

    public static void callee(Object param1, Object param2) {
        Object local = new Object();
        System.out.println(local);
    }
}
