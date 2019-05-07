package unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/5/25
 * Time: 15:19
 * Desc:  unsafe 带 ordered， volatile，cas的操作都是native
 */
public class Unsafe {

    private static final int THREAD_NUM = 20;

    private static CyclicBarrier cb = new CyclicBarrier(THREAD_NUM);

    public static void main(String[] args) throws Exception{
        testMemoryAllocate();
    }



    static class MyThread implements Runnable{
        public long i = 0; //值没有算错
        private static long offset;

        public long getI() {
            return i;
        }

        static{
            try {
                Field f = MyThread.class.getDeclaredField("i");
                offset = unsafe.objectFieldOffset(f);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            try {
                cb.await();
            }catch (Exception e){
                e.printStackTrace();
            }

            int count = 0;
            int loop = 0;
            while(count < 10000) {
                long h = i;
                if(unsafe.compareAndSwapLong(this, offset, h, h + 1))//好像提供了volatile的语义???
                    count++;
                loop++;//随着线程变多，循环圈数变大，loop的值变大一倍以上
            }
            System.out.println("loop "+ loop);
        }
    }

    private static void testCASwithNormalVar() throws Exception{
        MyThread t = new MyThread();
        List<Thread> pool = new ArrayList<>();
        for(int i = 0; i< THREAD_NUM; i++){
            pool.add(new Thread(t));
        }

        for(Thread th: pool){
            th.start();
        }
        for(Thread th: pool){
            th.join();
        }
        System.out.println(t.getI());
    }

    private static void testFieldOffset() throws Exception{
        UnsafeTest testO = new UnsafeTest();
        Field test = UnsafeTest.class.getDeclaredField("test");
        Field count = UnsafeTest.class.getDeclaredField("count");
        long testOff = unsafe.objectFieldOffset(test);
        long countOff = unsafe.staticFieldOffset(count);
        System.out.println(testOff);
        System.out.println(countOff);
        unsafe.putInt(testO, testOff, 10);
        unsafe.putInt(UnsafeTest.class,countOff,20);
        System.out.println(testO);
    }

    private static void testMemoryAllocate(){
        long oneHundred = 1073774772L;
        byte size = 8;
        System.out.println(unsafe.addressSize());
        System.out.println(unsafe.pageSize());
        /*
         * 调用allocateMemory分配内存
         */
        long memoryAddress = unsafe.allocateMemory(size);//allocate bytes, but actually more then {param} byte
        long memoryAddress1 = unsafe.allocateMemory(size);
        long read = unsafe.getAddress(memoryAddress);
        long read1 = unsafe.getAddress(memoryAddress1);
        System.out.println("Val : " + read);
        System.out.println("Val : " + read1);

        //地址相差16byte
        System.out.println("Val : " + memoryAddress);
        System.out.println("Val : " + memoryAddress1);
        System.out.println("Val : " + Long.toHexString(memoryAddress));
        System.out.println("Val : " + Long.toHexString(memoryAddress1));
        /*
         * 将100写入到内存中
         */
        unsafe.putAddress(memoryAddress, oneHundred);
        unsafe.putAddress(memoryAddress1, oneHundred);

        System.out.println("Val : " + Long.toHexString(memoryAddress));
        System.out.println("Val : " + Long.toHexString(memoryAddress1));

        unsafe.putByte(memoryAddress, (byte) 1);
        unsafe.putByte(memoryAddress + 1, (byte) 1);//memoryAddress + 1 只挪动一个byte的位移,putByte/putInt会把挪动后的内存设置为入参的值，putByte设置8个字节，putInt设置32个字节
        //unsafe.putInt(memoryAddress + 1,1);
        /*
         * 内存中读取数据
         */
        long readValue = unsafe.getAddress(memoryAddress);
        long readValue1 = unsafe.getAddress(memoryAddress1);
        System.out.println("Val : " + readValue);
        System.out.println("Val : " + readValue1);
        //1073742081
        //1073774772
        unsafe.freeMemory(memoryAddress);
        unsafe.freeMemory(memoryAddress1);
    }

    private static sun.misc.Unsafe unsafe;

    static
    {
        try
        {
            Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (sun.misc.Unsafe) field.get(null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class UnsafeTest{
    private int test = 0;
    private static long count = 0L;

    @Override
    public String toString() {
        return "test="+ test
                + "count="+count;
    }
}
