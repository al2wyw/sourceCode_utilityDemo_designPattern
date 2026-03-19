package jvm;

/**
 * c1 has IET(implicit exception table), compiled frame when exception occurs
 * c2 has IET, interpreted frame when exception occurs ??? maybe deopt
 * -XX:CompileCommand=compileonly,*ImplicitExceptionTest.* -XX:-BackgroundCompilation -XX:CompileThreshold=10000 -XX:+PrintCompilation jvm.ImplicitExceptionTest 100000 100001
 * make count and loop small to just trigger only level 3 compile
 * */
public class ImplicitExceptionTest {

    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);
        int loop = Integer.parseInt(args[1]);
        int ret = 0xabc;
        for (int i = 0; i < loop; i++) {
            ret = ret / count;
            count--;
        }

        System.out.println(ret);
    }
}
