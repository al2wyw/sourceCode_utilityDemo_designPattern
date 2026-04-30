package demo;

/**
 * Created by johnny.ly on 2025/6/14.
 *
 * -XX:-BackgroundCompilation -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=compileonly,*testExceptionWithProfileDeep.* -XX:CompileCommand=dontinline,*testExceptionWithProfileDeep.* -XX:+LogCompilation -XX:LogFile=./mylogfile.log -XX:+PrintAssembly -XX:+PrintStubCode
 */
public class testExceptionWithProfileDeep {

    private static int expCount = 0;

    private static int errCount = 0;

    private static final int LOOP = 9999999; //0x98967F

    private static final int LOOP_1 = LOOP + 1; //0x989680

    private static final ArrayData[] ARRAY = new ArrayData[]{new ArrayData(), null};

    public static void main(String[] args) {
        // loop = LOOP + 3
        int loop = Integer.parseInt(args[0]);

        // for profile
        for (int i = 0; i < LOOP; i++) {
            test(i);
        }

        // for compile code to run
        for (int i = 0; i < loop; i++) {
            test(i);
        }

        for (int i = 0; i < loop; i++) {
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
            if (expCount > 0x111) {
                throw e;
            }
            e.printStackTrace();
        } catch (RuntimeException e) {
            errCount++;
            if (errCount > 0x222) {
                throw e;
            }
            e.printStackTrace();
        }
        return 0x1000;
    }

    public static int inner(int i) {
        // LOOP trigger throw
        if (i == LOOP) {
            throw new RuntimeException(); //c1 unwind exp(no exp tab), c2 uncommon -> c2 rethrow
        }

        // LOOP + 1 trigger div zero
        int j = 0x600 / (LOOP_1 - i); //c1 implicit exp, c2 uncommon -> c2 rethrow

        // LOOP + 2 trigger npe
        // LOOP + 3 trigger out range
        int index = i - LOOP_1;
        if (index < 0) {
            index = 0;
        }
        ArrayData o = ARRAY[index];//c1 implicit exp, c2 uncommon -> c2 rethrow

        return o.value + j + i;//c1 implicit exp, c2 uncommon -> c2 rethrow
    }

    public static class ArrayData {
        public int value;
    }
}