package jvm;

/**
 *
 * -XX:+UnlockDiagnosticVMOptions -Xcomp -XX:TieredStopAtLevel=1 -XX:+PrintCompilation  -XX:CompileCommand=compileonly,*CompilerC1Test.* -XX:+PrintInitialBlockList -XX:+TraceLivenessGen -XX:+PrintIR
 *
 * */
public class CompilerC1Test {

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
