package jvm;

import utils.ThreadUtils;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by johnny.ly on 2016/6/26.
 */
public class testWeakGC {
    //只有full gc才会回收weak reference，full gc后还没有空间则会回收soft reference
    private static List<WeakReference<Object>> object1s = new LinkedList<WeakReference<Object>>();

    public static void main(String[] args) throws Exception{
        ThreadUtils.sleep(10000);

        object1s.add(new WeakReference<Object>(new MyObject()));
        object1s.add(new WeakReference<Object>(new MyObject()));
        object1s.add(new WeakReference<Object>(new MyObject()));

        for(int i=0;i<1000;i++){
            MyObject o = new MyObject();
            System.out.println(o);
            o = null;
            //ThreadUtils.sleep(3000);
            LockSupport.parkNanos(3000000000L);
        }
    }
}
