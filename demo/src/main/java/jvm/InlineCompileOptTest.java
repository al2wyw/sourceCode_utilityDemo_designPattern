package jvm;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/1/19
 * Time: 11:13
 * Desc:
 -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=compileonly,*InlineCompileOptTest.* -XX:CompileCommand=compileonly,*Adder.* -XX:CompileCommand=compileonly,*Object.*
 -XX:-BackgroundCompilation -XX:+PrintCompilation -XX:+PrintInlining -XX:CompileThreshold=10000
 -XX:+TraceDeoptimization
 -XX:+LogCompilation -XX:LogFile=./mylogfile.log -XX:+PrintAssembly -XX:+PrintStubCode -XX:+PrintInterpreter

 为什么ArithmeticException发生时栈是Interpreted?
 c2 osr level 4 生成的代码好复杂，包含inline和非inline的代码 !!!
 c2 编译没有implicit exception handler(除非null check optimized)
 c2 会对divide by zero进行显式检测触发uncommon trap退优化成解释执行，由Interpreter生成的汇编代码触发SIGFPE

 inlined method trace frame (exception) PcDesc and ScopeDesc in nmethod refer to java_lang_Throwable::fill_in_stack_trace

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