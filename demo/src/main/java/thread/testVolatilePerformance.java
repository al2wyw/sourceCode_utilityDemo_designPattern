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
 *       http://ifeve.com/juc-atomic-class-lazyset-que/
 *
 * 好像lazySet的耗时更高: ???
 * x86_64
 * 169482
 * 290787
 * 168343
 * 188324
 * 447588
 * 601531
 * 490
 * -Xint
 * 140995
 * 207650
 * 132392
 * 150200
 * 1238724
 * 3537124
 * 100243
 * -Xcomp -XX:CompileCommand=compileonly,*testVolatilePerformance.*
 * 17392
 * 1214462
 * 7422
 * 11966
 * 1229170
 * 3797356
 * 545
 */
public class testVolatilePerformance {

    private static AtomicInteger int1 = new AtomicInteger(1);
    private static AtomicInteger int2 = new AtomicInteger(1);

    private static volatile boolean test1 = false;

    private static boolean test2 = false;

    public static void main(String args[]) throws Exception{
        int loop = 1000_0000;
        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i = 0; i < loop; i++){
            if(test1){
                System.out.println("test1");
            }
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS)); // 52039

        stopwatch.reset();
        stopwatch.start();
        for(int i = 0; i < loop; i++){
            if(int1.get() == 10){
                System.out.println("int1");
            }
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS)); // 85719

        stopwatch.reset();
        stopwatch.start();
        for(int i = 0; i < loop; i++){
            if(test2){
                System.out.println("test2");
            }
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));// 4803


        stopwatch.reset();
        stopwatch.start();
        for(int i = 0; i < loop; i++){
            test1 = true;
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));// 273493

        stopwatch.reset();
        stopwatch.start();
        for(int i = 0; i < loop; i++){
            int2.set(3);
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));// 1137729

        stopwatch.reset();
        stopwatch.start();
        for(int i = 0; i < loop; i++){
            int1.lazySet(2);
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));// 89829


        stopwatch.reset();
        stopwatch.start();
        for(int i = 0; i < loop; i++){
            test2 = true;
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));// 10861
    }

}
