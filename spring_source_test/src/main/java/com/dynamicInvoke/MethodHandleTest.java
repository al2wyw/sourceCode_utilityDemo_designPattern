package com.dynamicInvoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/22
 * Time: 21:03
 * Desc: failed!!!
 */
public class MethodHandleTest {
    public static void main( String args[] ) throws Exception{
        (new MethodHandleTest().new Son()).action();
    }

    public class GrandFather{
        public void action(){
            System.out.println("grand father action!");
        }
    }

    public class Father extends GrandFather{
        public void action(){
            System.out.println("father action!");
        }
    }

    public class Son extends Father{
        //call the action of GrandFather
        public void action() {
            //super just can refer to father, cannot touch grand father
            try {
                MethodType type = MethodType.methodType(void.class);
                MethodHandle handle = MethodHandles.lookup().findSpecial(GrandFather.class, "action", type, getClass());
                handle.invoke(this);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}