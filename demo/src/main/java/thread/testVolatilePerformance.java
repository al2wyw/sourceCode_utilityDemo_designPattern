package thread;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/5/7
 * Time: 14:22
 * Desc: AtomicReference的lazySet的意义 volatile的get本身就是直接去主存取数 ???
 *       volatile-read LL LS
 *       SS volatile-write SL
 *       SS lazySet ???
 */
public class testVolatilePerformance {

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
            if(performance.test2){
                System.out.println("test2");
            }
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));// 4803
    }
}
