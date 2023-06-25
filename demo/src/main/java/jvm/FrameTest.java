package jvm;

/**
 * Created with IntelliJ IDEA.
 * User: liyang
 * Date: 2023-06-12
 * Time: 19:58
 * Description:
 * -Djava.library.path=/Users/liyang/IdeaProjects/sourceCode_utilityDemo_designPattern/demo/src/main/java/native
 */
public class FrameTest {

    static {
        System.loadLibrary("frameTest");
    }

    public static native int nativeCall(int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8);

    public static void main(String[] args) {
        int loc1 = 1;
        int loc2 = 2;
        int loc3 = 3;
        int loc4 = 4;
        int loc5 = 5;
        int loc6 = 6;
        int loc7 = 7;
        int loc8 = 8;
        int ret = caller(loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8);

        System.out.printf("main ret %d\n", ret);
    }

    public static int caller(int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8) {
        int loc1 = arg1 + 16;
        int loc2 = arg2 + 16;
        int loc3 = arg3 + 16;
        int loc4 = arg4 + 16;
        int loc5 = arg5 + 16;
        int loc6 = arg6 + 16;
        int loc7 = arg7 + 16;
        int loc8 = arg8 + 16;
        //return callee(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
        return arg1 + callee(loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8);
    }

    public static int callee(int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8) {
        int loc1 = arg7 + arg8;
        arg1 = arg1 + 16;
        return loc1 + nativeCall(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
    }
}
