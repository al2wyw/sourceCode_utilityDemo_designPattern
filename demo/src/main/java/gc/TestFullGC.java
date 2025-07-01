package gc;

import demoObject.BigObject;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/5/16
 * Time: 13:37
 * Desc: -Xms20M -Xmx20M -Xmn10m -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:MaxTenuringThreshold=1 -XX:+UseSerialGC -XX:+PrintTenuringDistribution -XX:+PrintHeapAtGC -verbose.gc
 * 搞不清楚...
 */
public class TestFullGC {
    private static final int MB = 1024 * 1024;

    public static void main(String[] args) throws Exception
    {
        /*Thread.sleep(10000);
        System.out.println("start");*/
        System.gc();
        // 模拟fullgc场景
        // 提升担保
        // 提升担保
        byte[] M6, M3, M4, M5, M7, M8;
        M6 = new byte[6 * MB];
        M6[0] = 0;
        M6[0] = 0;
        // 使用2次保证下次需要的时候可以晋升到老年代 会晋升那么 晋升经验值为6M
        sleep();
        M3 = new byte[4 * MB];
        sleep();
        M4 = new byte[2 * MB];
        sleep();
        M4 = null;
        M5 = new byte[2 * MB];
        M5[0] = 0;
        M5[0] = 0;
        sleep();
        M7 = new byte[2 * MB];
        M7[0] = 0;
        M7[0] = 0;
        M7 = null;
        sleep();
        M8 = new byte[3 * MB];
        // 最终如下对象 老年代 M6 + M8 = 9M
        // 年轻代:M3 + M5 = 6M = 6144K
        System.out.println("M6 HEX:0x" + Long.toHexString(addressOf(M6)));
        System.out.println("M5 HEX:0x" + Long.toHexString(addressOf(M5)));
        System.out.println("M3 HEX:0x" + Long.toHexString(addressOf(M3)));
        System.out.println("M8 HEX:0x" + Long.toHexString(addressOf(M8)));


    }

    private static void addressChange() throws Exception {
        BigObject bigObject = new BigObject();
        BigObject bigObject1 = bigObject;
        System.out.println("bigObject HEX:0x" + Long.toHexString(addressOf(bigObject)));
        System.out.println("bigObject1 HEX:0x" + Long.toHexString(addressOf(bigObject1)));
        System.gc();
        System.out.println("bigObject HEX:0x" + Long.toHexString(addressOf(bigObject)));
        System.out.println("bigObject1 HEX:0x" + Long.toHexString(addressOf(bigObject1)));
    }

    private static void sleep() throws Exception{
        Thread.sleep(1000);
        System.out.println("wait up");
    }

    private static Unsafe unsafe;

    static
    {
        try
        {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static long addressOf(Object o) throws Exception
    {
        Object[] array = new Object[] { o };

        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize)
        {
            case 4:
                objectAddress = unsafe.getInt(array, baseOffset);
                break;
            case 8:
                objectAddress = unsafe.getLong(array, baseOffset);
                break;
            default:
                throw new Error("unsupported address size: " + addressSize);
        }

        return (objectAddress);
    }

}
