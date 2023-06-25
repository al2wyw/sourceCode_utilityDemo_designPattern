# include <stdio.h>
# include "FrameTest.h"
# include <jni.h>

/*
 javah -jni jvm.FrameTest
 gcc -m64 -I/Users/liyang/jdk8/Contents/Home/include -I/Users/liyang/jdk8/Contents/Home/include/darwin FrameTest.c -dynamiclib -o libframeTest.dylib
*/

int callee(int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8)
{
    int loc1 = arg7 + arg8;
    arg1 = arg1 + 16;
    return loc1 + arg1;
}

int caller(int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8)
{
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

JNIEXPORT jint JNICALL Java_jvm_FrameTest_nativeCall
  (JNIEnv * env, jclass klass, jint arg1, jint arg2, jint arg3, jint arg4, jint arg5, jint arg6, jint arg7, jint arg8){
    int loc1 = arg1 + 16;
    int loc2 = arg2 + 16;
    int loc3 = arg3 + 16;
    int loc4 = arg4 + 16;
    int loc5 = arg5 + 16;
    int loc6 = arg6 + 16;
    int loc7 = arg7 + 16;
    int loc8 = arg8 + 16;
    int ret = caller(loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8);

    printf("native call %d\n", ret);

    return ret;
}