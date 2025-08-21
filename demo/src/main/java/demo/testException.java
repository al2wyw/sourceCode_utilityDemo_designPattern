package demo;

/**
 * Created by johnny.ly on 2025/6/14.
 *
 * -Xcomp -XX:-BackgroundCompilation -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=compileonly,*testException.* -XX:+LogCompilation -XX:LogFile=./mylogfile.log -XX:+PrintAssembly -XX:+PrintStubCode
 */
public class testException {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            test(i);
        }
    }

    public static int test(int i){
        try {
            return inner(i);
        } catch (Exception e) {
            System.out.println("exc called");
            return 10;
        } finally {
            System.out.println("finally called");
            return 100;
        }
    }

    public static int inner(int i){
        System.out.println("inner called " + i);
        if (i == 7) {
            throw new RuntimeException();
        }
        return 10 / (i - 8);
    }
}