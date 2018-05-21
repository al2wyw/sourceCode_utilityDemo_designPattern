package jvm;

import demoObject.BigObject;
import utils.GCUtils;
import utils.ThreadUtils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by johnny.ly on 2016/6/26.
 * 只有full gc才会回收weak reference，full gc后还没有空间则会回收soft reference(由jvm决定是否没有空间，明明有空间也回收)
 * 1. Weak和Soft会自动清除引用,在gc时会被enqueue，在enqueue之前清除引用，所以poll之后get()返回null
 * 2. pham不会自动清除引用,当确定会被垃圾回收时就会被enqueue，而不是等到gc时才去enqueue，类似于"垃圾回收通知"
 * 3. ReferenceHandler thread 负责把reference进行enqueue
 * 4. FinalizerThread 负责从queue中remove，并调用对象finializer方法

 testWeakGC,testPhamGC,testSoftGC 统一使用以下参数
 -Xms10m
 -Xmx10m
 -XX:+PrintGCDetails
 -XX:+PrintGCTimeStamps
 *
 */
public class testWeakGC {
    private static List<WeakReference<Object>> object1s = new LinkedList<WeakReference<Object>>();

    private static ReferenceQueue<Object> queue = new ReferenceQueue<>();

    public static void main(String[] args) throws Exception{

        object1s.add(new WeakReference<Object>(new BigObject(),queue));
        object1s.add(new WeakReference<Object>(new BigObject(),queue));
        object1s.add(new WeakReference<Object>(new BigObject(),queue));

        for(int i=0;i<1000;i++){
            BigObject o = new BigObject();
            System.out.println(o);
            o = null;
            GCUtils.gcWithSleep();
            Object r;
            while((r = queue.poll())!=null){
                WeakReference<Object> mr = (WeakReference<Object>)r;
                System.out.println("object from queue " + mr.get() + " " + mr.get());
            }
            //ThreadUtils.sleep(3000);
            LockSupport.parkNanos(1000000000L);
        }
    }
}
