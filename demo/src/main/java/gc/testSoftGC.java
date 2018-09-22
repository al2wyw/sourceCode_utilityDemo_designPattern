package gc;

import demoObject.BigObject;
import utils.GCUtils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by johnny.ly on 2016/6/26.
 */
public class testSoftGC {

    private static List<MySoftReference<Object>> object1s = new LinkedList<MySoftReference<Object>>();

    private static ReferenceQueue<Object> queue = new ReferenceQueue<>();

    public static void main(String[] args) throws Exception{

        object1s.add(new MySoftReference<Object>(new BigObject(),queue,"test1"));
        object1s.add(new MySoftReference<Object>(new BigObject(),queue,"test2"));
        object1s.add(new MySoftReference<Object>(new BigObject(),queue,"test3"));

        for(int i=0;i<1000;i++){
            BigObject o = new BigObject();
            System.out.println(o);
            o = null;
            GCUtils.gcWithSleep();
            Object r;
            while((r = queue.poll())!=null){
                MySoftReference<Object> mr = (MySoftReference<Object>)r;
                System.out.println("object from queue " + mr.get() + " " + mr.getName());
            }
            LockSupport.parkNanos(1000000000L);
        }
    }
}

class MySoftReference<T> extends SoftReference<T>{

    private String name;

    public MySoftReference(T referent, ReferenceQueue<? super T> q, String name) {
        super(referent, q);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
