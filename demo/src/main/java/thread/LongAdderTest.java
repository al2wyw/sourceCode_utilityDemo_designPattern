package thread;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/5/28
 * Time: 18:58
 * Desc:
 */
public class LongAdderTest {

    private static LongAdder longAdder = new LongAdder();

    public static void main(String[] args) throws Exception{
        CyclicBarrier barrier = new CyclicBarrier(2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    barrier.await();
                    for (int i = 0; i < 99999; i++) {
                        longAdder.add(1);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        barrier.await();
        for(int i = 0;i<99999; i++) {
            longAdder.add(1);
        }
        System.out.println(longAdder.sum());
    }
}
