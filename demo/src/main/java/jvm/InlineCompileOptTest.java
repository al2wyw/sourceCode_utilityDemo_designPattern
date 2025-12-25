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
 -XX:-BackgroundCompilation

 inlined method trace frame (exception) ??? OmitStackTraceInFastThrow 默认打开
 <a href="https://stackoverflow.com/questions/7218575/how-can-methods-throwing-exceptions-be-inlined">...</a>
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

/**
 * -XX:+PrintCompilation
 * 27 7 % 3 OSRExample::main @ 4 (32 bytes)
 * %: Indicates an OSR compilation.
 * @ 4: Shows the bytecode index (bci) where the OSR occurs (usually a backward branch in a loop).
 * 7: Compilation ID.
 * 3: Tiered compilation level.
 * */