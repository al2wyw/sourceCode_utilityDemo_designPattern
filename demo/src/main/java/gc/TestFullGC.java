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
        addressChange();

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
