package gc;

import demoObject.BigObject;
import demoObject.SmallObject;
import utils.GCUtils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.PhantomReference;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by johnny.ly on 2016/6/26.
 * BigObject没有被回收是因为被object1s引用了
 */
public class testPhamGC {

    private static List<PhantomReference<Object>> object1s = new LinkedList<PhantomReference<Object>>();

    private static ReferenceQueue<Object> queue = new ReferenceQueue<>();

    public static void main(String[] args) throws Exception{

        object1s.add(new PhantomReference<Object>(new BigObject(),queue));
        object1s.add(new PhantomReference<Object>(new BigObject(),queue));
        object1s.add(new PhantomReference<Object>(new BigObject(),queue));

        for(int i=0;i<1000;i++){
            SmallObject o = new SmallObject();
            System.out.println(o);
            o = null;
            Object r;
            //GCUtils.gcWithSleep();
            while((r = queue.poll())!=null){
                PhantomReference<Object> mr = (PhantomReference<Object>)r;
                System.out.println("object from queue " + mr.get() + " " + mr.get());
            }
            LockSupport.parkNanos(1000000000L);
        }
    }
}
