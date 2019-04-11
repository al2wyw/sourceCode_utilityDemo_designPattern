package thread;

import com.google.common.base.Stopwatch;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/4/10
 * Time: 12:32
 * Desc:
 *       i < 0 || i >= n || i + n >= nextn maybe a bug, i >= n || i + n >= nextn can not achieve
 *       sc == rs + 1 || sc == rs + MAX_RESIZERS is a bug, rs should be resizeStamp(n) << RESIZE_STAMP_SHIFT
 *
 */
public class ConHashMapTest {

    public static void main(String[] args) throws Exception{
        CountDownLatch latch = new CountDownLatch(1);
        ConcurrentHashMap<String ,Integer> values = new ConcurrentHashMap<>(4);

        new Thread(() -> {
            try {
                latch.await();
                Stopwatch stopwatch = Stopwatch.createStarted();//3145728 table[4194303] - > 1769191
                values.put("1769191",100);
                System.out.println(stopwatch.stop().elapsed(TimeUnit.MICROSECONDS));
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();

        for(int i = 0; i< 3145727; i++) {
            values.put(String.valueOf(i), i);
        }
        latch.countDown();
        values.put("res",343444);//100 ns VS 100000 ns
    }
}
