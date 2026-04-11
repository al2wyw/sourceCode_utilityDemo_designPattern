package jvm;

/**
 *
 * -XX:+UnlockDiagnosticVMOptions -Xcomp -XX:TieredStopAtLevel=1 -XX:+PrintCompilation  -XX:CompileCommand=compileonly,*CompilerC1Test.* -XX:+PrintInitialBlockList -XX:+TraceLivenessGen -XX:+PrintIR -XX:+PrintCFG
 * -XX:-TieredCompilation -XX:+PrintIdeal -XX:PrintIdealGraphFile=ideal.xml -XX:PrintIdealGraphLevel=1
 * */
public class CompilerTest {

    public static void main(String[] args) {
        System.out.println(test(5));
    }

    public static int test(int n) {
        int p = 1;
        while (n > 0) {
            p = p * n;
            n = n - 1;
        }
        return p;
    }
}
