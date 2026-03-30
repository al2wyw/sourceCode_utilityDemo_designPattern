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
 * 好像lazySet的耗时更高:
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
        // lazySet 受到 osr 编译影响， call site not reached 无法inline，后面被修复了 https://bugs.openjdk.org/browse/JDK-8014830
        /*Stopwatch stopwatch = Stopwatch.createStarted();
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
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));// 10861*/

        // 改成逐个方法osr编译，互不影响，多次循环warmup，取最后一次的时间，但是受循环展开影响也无法准确测出结果
        int run = Integer.parseInt(args[0]);
        for (int i = 0; i < run; i++) {
            test2(loop);// normal get
            test1(loop);// volatile get
            int1(loop);// atomic get
            test2Set(loop); // normal set
            int1Lazy(loop); // atomic lazy set
            test1Set(loop); // volatile set
            int2Set(loop); // atomic set
            test1 = false;
            test2 = false;
            System.out.println();
        }
    }

    private static void test1(int loop){
        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i = 0; i < loop; i++){
            if(test1){ //循环展开，无lock前缀指令
                System.out.println("test1");
            }
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS) + " volatile get");
    }

    private static void int1(int loop){
        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i = 0; i < loop; i++){
            if(int1.get() == 10){ //循环展开，无lock前缀指令
                System.out.println("int1");
            }
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS)+ " atomic get");
    }

    private static void test2(int loop){
        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i = 0; i < loop; i++){
            if(test2){//循环展开
                System.out.println("test2");
            }
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS) + " normal get");
    }

    private static void test1Set(int loop){
        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i = 0; i < loop; i++){
            test1 = true; //循环展开，由于绕不开lock前缀指令，性能较差
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS) + " volatile set");
    }

    private static void int2Set(int loop){
        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i = 0; i < loop; i++){
            int2.set(3); //inline后还是volatile set
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS) + " atomic set");
    }

    private static void int1Lazy(int loop){
        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i = 0; i < loop; i++){
            int1.lazySet(2); //inline后无lock前缀，循环展开
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS) + " atomic lazy set");
    }

    private static void test2Set(int loop){
        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i = 0; i < loop; i++){
            test2 = true; //循环展开
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS) + " normal set");
    }
}

/**
 *
 * -XX:CompileCommand=compileonly,*testVolatilePerformance.*  -XX:CompileCommand=compileonly,*AtomicInteger.* -XX:CompileCommand=compileonly,*Unsafe.* thread.testVolatilePerformance
 * (the last run: slowdebug)
 * 7254 normal get
 * 7261 volatile get
 * 7343 atomic get
 * 226 normal set
 * 3736 atomic lazy set
 * 11920 volatile set
 * 65868 atomic set
 *
 *
 * (the last run: tjdk)
 * 0 normal get //dead code eliminate
 * 2157 volatile get
 * 3659 atomic get
 * 231 normal set
 * 3622 atomic lazy set
 * 11803 volatile set //loop unroll -XX:LoopUnrollLimit=0 disable
 * 66560 atomic set
 * */
