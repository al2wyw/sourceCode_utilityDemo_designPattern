package jvm;

/**
 * -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation -XX:LogFile=./mylogfile.log -XX:+PrintAssembly -XX:+PrintStubCode -XX:CompileCommand=dontinline,*RegisterMapTest.*  -XX:CompileCommand=compileonly,*RegisterMapTest.*
 * */
public class RegisterMapTest {

    private static int count;
    private static final int LOOP = 100000;
    private static final int GC_INTERVAL = LOOP / 10;

    public static void main(String[] args) {
        for (int i = 0; i < LOOP; i++) {
            caller();
            count++;
        }
    }

    public static void caller() {
        Object local = new Object();
        Object param1 = new Object();
        Object param2 = new Object();
        callee(param1, param2);//local saved on stack not in registers
        System.out.println(local);
    }

    public static void callee(Object param1, Object param2) {
        Object local = new Object();
        if (count % GC_INTERVAL == 0) {
            System.gc();
        }
        System.out.println(local);
    }
}
