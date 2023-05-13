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
 */
public class InlineCompileOptTest {

    private static Adder adder;

    public static void main(String[] args)throws Exception{
        int j = 0;
        for(int i = 0; i < 100000; i++){
            j = addOut(i, j);
        }
    }

    public static int addOut(int i, int j) {
        adder = new Adder(j); //<init> inline hot
        return adder.add(i, j);
    }
}