package demo;

/**
 * Created by johnny.ly on 2025/6/14.
 *
 * -XX:-BackgroundCompilation -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=compileonly,*testExceptionWithProfile.* -XX:+LogCompilation -XX:LogFile=./mylogfile.log -XX:+PrintAssembly -XX:+PrintStubCode
 */
public class testExceptionWithProfile {

    private static int expCount = 0;

    private static int errCount = 0;

    private static final int LOOP = 999999999;

    public static void main(String[] args) {
        // for profile
        for (int i = 0; i < LOOP; i++) {
            test(i);
        }

        // for compile code to run
        for (int i = 0; i < LOOP; i++) {
            test(i);
        }

        System.out.println(expCount);
        System.out.println(errCount);
    }

    public static int test(int i){
        try {
            return inner(i);
        } catch (ArithmeticException e) {
            expCount++;
            if (expCount > 0x213) {
                throw e;
            }
        } catch (RuntimeException e) {
            errCount++;
            if (errCount > 0x323) {
                throw e;
            }
        }
        return 0x1000;
    }

    public static int inner(int i) {
        if (i > LOOP) {
            throw new RuntimeException(); //c1 unwind exp, c2 rethrow
        }

        if (i == 7) {
            throw new RuntimeException(); //c1 unwind exp, c2 rethrow
        }

        int j = 0x600 / (i - 8); //c1 implicit exp, c2 rethrow

        return j + i;
    }
}