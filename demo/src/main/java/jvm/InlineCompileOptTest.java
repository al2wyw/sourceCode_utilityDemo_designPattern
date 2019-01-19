package jvm;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/1/19
 * Time: 11:13
 * Desc:
 -XX:CompileThreshold=10
 -XX:+PrintCompilation
 -XX:+UnlockDiagnosticVMOptions
 -XX:+PrintInlining
 failed !!!
 */
public class InlineCompileOptTest {

    public static void main(String[] args)throws Exception{
        Adder adder = new Adder();
        for(int i = 0; i < 1000; i++){
            int j = adder.add(i, i + 1);
            System.out.println(j);
        }
        Thread.sleep(1000);
    }
}
