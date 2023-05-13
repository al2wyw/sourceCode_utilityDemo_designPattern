package thread;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/10/29
 * Time: 12:30
 * Desc:
 */
public class ThreadInterruptVisibleTest {

    private static final int VALUE = 100000;
    private static final int THREAD_NUMS = 6;
    private static int flag = 0;

    private static int sentinel = VALUE;
    private static int sentinel_add = 0;

    public static void main(String args[]) throws Exception{
        Thread[] threadTarget = new Thread[THREAD_NUMS];

        for (int i = 0; i < THREAD_NUMS; i++) {
            final int id = i;
            threadTarget[i] = new Thread(() -> {
                do {

                    while (!Thread.interrupted());//keep busy


                    if (flag == VALUE) {
                        System.out.printf(" test value %d, %d, %d, %d\n", id, flag, sentinel, sentinel_add);
                        break;
                    }

                    sentinel_add++;
                    flag++;
                    sentinel--;

                    threadTarget[new Random().nextInt(THREAD_NUMS)].interrupt();//randomly peek one to start

                } while (true);
            });
        }
        for (int i = 0; i < THREAD_NUMS; i++) {
            threadTarget[i].start();
        }
        threadTarget[0].interrupt();//start to work

        Thread.sleep(1000);//wait to finnish

        for (int i = 0; i < THREAD_NUMS; i++) {
            threadTarget[i].interrupt();
        }
    }
}
