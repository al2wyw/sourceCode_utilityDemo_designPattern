package thread;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/5/7
 * Time: 14:22
 * Desc: AtomicReference的lazySet的意义: 加快赋值操作， 对volatile的读操作无影响
 *       volatile-read LL LS
 *       SS volatile-write SL
 *       SS lazySet
 */
public class testVolatilePerformance {

    private AtomicInteger int1 = new AtomicInteger(1);
    private AtomicInteger int2 = new AtomicInteger(1);

    private volatile boolean test1 = false;

    private boolean test2 = false;

    public static void main(String args[]) throws Exception{
        testVolatilePerformance performance = new testVolatilePerformance();
        int loop = 99999999;
        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i = 0; i < loop; i++){
            if(performance.test1){
                System.out.println("test1");
            }
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS)); // 52039

        stopwatch.reset();
        stopwatch.start();
        for(int i = 0; i < loop; i++){
            if(performance.int1.get() == 10){
                System.out.println("int1");
            }
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS)); // 85719

        stopwatch.reset();
        stopwatch.start();
        for(int i = 0; i < loop; i++){
            if(performance.test2){
                System.out.println("test2");
            }
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));// 4803


        stopwatch.reset();
        stopwatch.start();
        for(int i = 0; i < loop; i++){
            performance.test1 = true;
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));// 273493

        stopwatch.reset();
        stopwatch.start();
        for(int i = 0; i < loop; i++){
            performance.int2.set(3);
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));// 1137729

        stopwatch.reset();
        stopwatch.start();
        for(int i = 0; i < loop; i++){
            performance.int1.lazySet(2);
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));// 89829


        stopwatch.reset();
        stopwatch.start();
        for(int i = 0; i < loop; i++){
            performance.test2 = true;
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));// 10861
    }

}
