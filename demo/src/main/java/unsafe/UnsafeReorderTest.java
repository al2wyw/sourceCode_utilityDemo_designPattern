package unsafe;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: liyang
 * Date: 2023-06-06
 * Time: 22:05
 * Description:
 *  ENV: Darwin Kernel Version 22.4.0: xnu-8796.101.5~3/RELEASE_X86_64 2.6 GHz 六核Intel Core i7
 *  无需jit编译相关的设置
 */
public class UnsafeReorderTest {

    private static sun.misc.Unsafe unsafe;

    static {
        try
        {
            Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (sun.misc.Unsafe) field.get(null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static int x = 0, y = 0;
    private static int a = 0, b =0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for(;;) {
            i++;
            x = 10; y =10;
            a = 0; b = 0;
            Thread one = new Thread(() -> {
                //由于线程one先启动，下面这句话让它等一等线程two. 读着可根据自己电脑的实际性能适当调整等待时间.
                shortWait(40000); //这一句非常重要，一定要调整好
                //store load store
                a = 1;
                //unsafe.fullFence(); //cpu 重排序，loadFence 和 storeFence 无效
                x = b;
            });

            Thread other = new Thread(() -> {
                b = 1;
                //unsafe.fullFence();
                y = a;
            });
            one.start();other.start();
            one.join();other.join();
            String result = "第" + i + "次 (" + x + "," + y + "）";
            if(x == 0 && y == 0) {
                System.err.println(result);
                break;
            } else {
                System.out.println(result);
            }
        }
    }

    public static void shortWait(long interval){
        long start = System.nanoTime();
        long end;
        do{
            end = System.nanoTime();
        }while(start + interval >= end);
    }

    public static class ReorderExample {
        int a = 0;
        boolean flag = false;

        public void writer() {
            //store store no cpu reordering
            a = 1;          // 1
            flag = true;    // 2
        }

        public void reader() {
            if (flag) {         // 3
                //int i = a + a; // 4
                if (a == 0) {
                    System.err.println("done");
                    System.exit(0);
                }
            }
        }

        public static void main(String[] args) throws Exception {
            for (;;) {
                ReorderExample example = new ReorderExample();
                new Thread(() -> {
                    shortWait(40000);
                    example.writer();
                }).start();
                new Thread(() -> {
                    example.reader();
                }).start();
            }
        }
    }
}
