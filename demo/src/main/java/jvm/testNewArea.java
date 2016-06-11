package jvm;

import utils.ThreadUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by johnny.ly on 2016/6/10.
 */
public class testNewArea {

    //private static List<MyObject> objects = new LinkedList<MyObject>();

    private static List<MyObject1> object1s = new LinkedList<MyObject1>();

    public static void main(String[] args) throws Exception{
        ThreadUtils.sleep(10000);

        object1s.add(new MyObject1());
        object1s.add(new MyObject1());
        object1s.add(new MyObject1());

        for(int i=0;i<1000;i++){
            MyObject o = new MyObject();
            System.out.println(o);
            o = null;
            //ThreadUtils.sleep(3000);
            LockSupport.parkNanos(3000000000L);
        }
    }
}

class MyObject{
    private byte[] value = new byte[1024*1024]; //1 mb

}

class MyObject1{
    private byte[] value = new byte[1024]; //1 kb

}