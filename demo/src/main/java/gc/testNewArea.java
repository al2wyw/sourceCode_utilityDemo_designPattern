package gc;

import demoObject.BigObject;
import demoObject.SmallObject;
import utils.ThreadUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by johnny.ly on 2016/6/10.
 */
public class testNewArea {

    //private static List<MyObject> objects = new LinkedList<MyObject>();

    private static List<SmallObject> object1s = new LinkedList<SmallObject>();

    public static void main(String[] args) throws Exception{
        ThreadUtils.sleep(10000);

        object1s.add(new SmallObject());
        object1s.add(new SmallObject());
        object1s.add(new SmallObject());

        for(int i=0;i<1000;i++){
            BigObject o = new BigObject();
            System.out.println(o);
            o = null;
            //ThreadUtils.sleep(3000);
            LockSupport.parkNanos(3000000000L);
        }
    }
}

/**
 * -Xms12M
 * -Xmx12M
 * -Xmn6M
 * -XX:SurvivorRatio=2
 * -XX:MaxTenuringThreshold=5
 * */