package jvm;

import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/5/7
 * Time: 14:22
 * Desc:
 * java -classpath ./guava-25.1-jre.jar:./ -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=compileonly,*BranchPredictTest.*  -XX:CompileCommand=compileonly,*Math.* -XX:-BackgroundCompilation -XX:+PrintCompilation -XX:+PrintInlining -XX:+TraceDeoptimization -XX:+TracePcPatching -XX:+TraceOnStackReplacement jvm.BranchPredictTest 31
 * -XX:+PrintDeoptimizationDetails
 * -XX:-UseLoopPredicate
 */
public class BranchPredictTest {

    private static boolean flag = false;

    public static void main(String args[]) throws Exception{
        int loop = 1000_0000;

        int run = Integer.parseInt(args[0]);
        for (int i = 0; i < run; i++) {
            predict(loop);
            System.out.println();
            flag = i % 10 == 9;
        }
    }


    private static void predict(int loop){
        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i = 0; i < loop; i++){
            if(flag){
                double ret = 0.1 / (i + 1);
                i += (int)Math.round(ret);
            }
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));
    }

    private static void predict2(int loop){
        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i = 0; i < loop; i++){
            if(flag){
                double ret = 0.1 / (i + 1);
                i += (int)Math.round(ret);
                return;
            }
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));
    }
}
