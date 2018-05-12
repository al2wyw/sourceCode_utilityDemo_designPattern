package utils;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by win10 on 2018/5/12.
 */
public class GCUtils {

    public static void gcWithSleep(){
        System.gc();
        LockSupport.parkNanos(100000000L);
    }
}