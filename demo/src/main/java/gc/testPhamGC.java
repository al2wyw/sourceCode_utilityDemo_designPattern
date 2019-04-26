package gc;

import demoObject.BigObject;
import demoObject.SmallObject;
import utils.GCUtils;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.PhantomReference;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by johnny.ly on 2016/6/26.
 * 使用sa来查看object
 */
public class testPhamGC {

    private static List<PhantomReference<Object>> object1s = new LinkedList<PhantomReference<Object>>();

    private static ReferenceQueue<Object> queue = new ReferenceQueue<>();

    private static Field ref;

    static{
        try {
            ref = Reference.class.getDeclaredField("referent");
            ref.setAccessible(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{

        //如果不放入object1s, 那么 full gc 时PhantomReference本身会被回收掉，也就不会被放入queue
        SmallObject smallObject = new SmallObject();
        object1s.add(new PhantomReference<Object>(smallObject,queue));
        object1s.add(new PhantomReference<Object>(new SmallObject(),queue));
        object1s.add(new PhantomReference<Object>(new SmallObject(),queue));
        smallObject = null; //young gc 为什么没有回收 smallObject, PhantomReference的引用对象需要至少两次gc,必须queue.poll后才能在第二次gc时被回收

        for(int i=0;i<1000;i++){
            BigObject o = new BigObject();
            System.out.println(o);
            o = null;
            Object r;
            //GCUtils.gcWithSleep();
            while((r = queue.poll())!=null){
                PhantomReference<Object> mr = (PhantomReference<Object>)r;
                System.out.println("object from queue " + mr.get() + " " + ref.get(mr));
            }
            LockSupport.parkNanos(1000000000L);
        }
    }
}
