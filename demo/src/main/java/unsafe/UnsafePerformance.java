package unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liyang
 * Date: 2023-05-09
 * Time: 19:07
 * Description:
 *
 * -server
 * -XX:CompileThreshold=1
 *
 *
 * -server
 * -Xcomp
 * -XX:+UnlockDiagnosticVMOptions
 * -XX:+PrintAssembly
 * -XX:CompileCommand=compileonly,*UnsafePerformance.main
 * -XX:+LogCompilation
 * -XX:LogFile=./mylogfile.log
 *
 * reorder instructions out of object construction function:
 * we get 0
 * we get 0
 * we get 0
 * 12435708
 * 3979750
 * 3539250
 * 3353209
 * 3153750
 * 这个测试主要关注unsafe提供的内存屏障防止重排序(另一个方案是object加volatile修饰符)
 * synchronized双重锁不会出现重排序，详见synchronized的底层实现ObjectMonitor::exit，会发现storeload内存屏障(网上说为了稳妥起见还是要加volatile修饰符，避免在不同的运行环境下行为有差异)
 */
public class UnsafePerformance {

    public static void main(String[] args) throws InterruptedException {
        final int THREADS_COUNT = 50;
        final int LOOP_COUNT = 10000;

        long sum = 0;
        long min = Integer.MAX_VALUE;
        long max = 0;

        for(int n = 0;n <= 100;n++) {
            final Container basket = new Container();
            List<Thread> putThreads = new ArrayList<Thread>();
            List<Thread> takeThreads = new ArrayList<Thread>();
            for (int i = 0; i < THREADS_COUNT; i++) {
                putThreads.add(new Thread() {
                    @Override
                    public void run() {
                        for (int j = 0; j < LOOP_COUNT; j++) {
                            basket.create();
                        }
                    }
                });
                takeThreads.add(new Thread() {
                    @Override
                    public void run() {
                        for (int j = 0; j < LOOP_COUNT; j++) {
                            int ret = basket.get().getStatus();
                            if (ret == 0) {
                                System.out.println("we get 0");
                            }
                        }
                    }
                });
            }
            long start = System.nanoTime();
            for (int i = 0; i < THREADS_COUNT; i++) {
                takeThreads.get(i).start();
                putThreads.get(i).start();
            }
            for (int i = 0; i < THREADS_COUNT; i++) {
                takeThreads.get(i).join();
                putThreads.get(i).join();
            }
            long end = System.nanoTime();
            long period = end - start;
            if(n == 0) {
                continue;	//由于JIT的编译，第一次执行需要更多时间，将此时间不计入统计
            }
            sum += (period);
            System.out.println(period);
            if(period < min) {
                min = period;
            }
            if(period > max) {
                max = period;
            }
        }
        System.out.println("Average : " + sum / 100);
        System.out.println("Max : " + max);
        System.out.println("Min : " + min);
    }

    public static class Container {

        public static class SomeThing {
            private int status1;
            private int status2;
            private int status3;
            private int status;

            public SomeThing() {
                status1 = 1;
                status2 = 1;
                status3 = 1;
                status = 2;
            }

            public int getStatus() {
                return status;
            }
        }

        private SomeThing object;
        private final static SomeThing NULL = new SomeThing();

        public void create() {
            object = new SomeThing();
        }

        public SomeThing get() {
            if (object == null) {
                return NULL;
            }
            return object;
        }


        /*
        public static class SomeThing {
            private int status;

            public SomeThing() {
                status = 1;
            }

            public int getStatus() {
                return status;
            }
        }

        private SomeThing object;

        private Object value;
        private static final sun.misc.Unsafe unsafe = getUnsafe();
        private static final long valueOffset;

        static {
            try {
                valueOffset = unsafe.objectFieldOffset(Container.class.getDeclaredField("value"));
            } catch (Exception ex) { throw new Error(ex); }
        }

        public void create() {
            SomeThing temp = new SomeThing();
            unsafe.storeFence();
            //unsafe.putOrderedObject(this, valueOffset, null);	//将value赋null值只是一项无用操作，实际利用的是这条语句的内存屏障
            object = temp;
        }

        public SomeThing get() {
            while (object == null) {
                Thread.yield();
            }
            return object;
        }


        public static sun.misc.Unsafe getUnsafe() {
            try {
                Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                return (sun.misc.Unsafe)f.get(null);
            } catch (Exception e) {
            }
            return null;
        }

         */
    }

}
