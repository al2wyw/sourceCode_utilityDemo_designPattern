package com.dynamicInvoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/22
 * Time: 21:03
 * Desc: failed!!! now works fine
 */
public class MethodHandleTest {
    public static void main( String args[] ) throws Throwable{
        new Son().action();

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findVirtual(String.class, "indexOf",
                MethodType.methodType(int.class, String.class, int.class));
        mh = mh.bindTo("Hello").bindTo("l");//调用接收者，第1个参数
        System.out.println(mh.invoke(3));//第2个参数



        Object rcvr = "a";
        try {
            MethodType mt = MethodType.methodType(int.class);
            MethodHandles.Lookup l = MethodHandles.lookup();
            MethodHandle mhr = l.findVirtual(rcvr.getClass(), "hashCode", mt);

            int ret;
            try {
                ret = (int)mhr.invoke(rcvr); //mhr.invokeExact(rcvr) -> WrongMethodTypeException
                System.out.println(ret);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        } catch (IllegalArgumentException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException x) {
            x.printStackTrace();
        }
    }

    public static class GrandFather{
        private GrandFather(){ //如果是正常的外部class，无法被继承

        }
        public void action(){
            System.out.println("grand father action!");
        }
    }

    public static class Father extends GrandFather{
        public void action(){
            System.out.println("father action!");
        }
    }

    public static class Son extends Father{
        //call the action of GrandFather
        public void action() {
            //super just can refer to father, cannot touch grand father
            try {
                MethodType type = MethodType.methodType(void.class);
                MethodHandles.Lookup lk = MethodHandles.lookup();
                MethodHandle handle = lk.in(Father.class).findSpecial(GrandFather.class, "action", type, Father.class);
                handle.invoke(this);

                Method method = GrandFather.class.getDeclaredMethod("action");
                method.invoke(new GrandFather());
                //method.invoke(this);//invokevirtual com.dynamicInvoke.MethodHandleTest.GrandFather.action

                new GrandFather().action();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}