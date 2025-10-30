package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;

/**
 * PooledByteBufAllocator:
 * numDirectArenas 控制 PoolArena 数组的大小，用于降低线程的并发度，当线程数大于Arena数量时多个线程共享同一个Arena
 * PoolArena -> PoolChunkList(按PoolChunk内存使用比例划分为多个list，从而避免list过长) -> PoolChunk(底层是DirectByteBuffer, 使用完全二叉树管理16m大小的内存，内存集中在其叶子节点(8k大小))
 * PoolThreadCache:
 * free list of all sizes memory segment
 * 4种对象大小: tiny < 512b <= small < 8kb(page size) <= normal <= 16mb(chunk size) < huge(unpooled)
 */
public class ByteBufTest {

    private static final int KB = 1024;

    public static void main(String[] args) throws Exception {

        allocRunOut();
    }

    public static void test() {
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(9 * KB);

        String content = "test new days";
        byteBuf.setBytes(0, content.getBytes(StandardCharsets.UTF_8)); //跟rw index无关

        System.out.println(byteBuf);
        System.out.println(byteBuf.readableBytes());
        System.out.println(byteBuf.toString(0, content.length(), StandardCharsets.UTF_8)); //跟rw index无关

        byteBuf.writeBytes("test".getBytes(StandardCharsets.UTF_8)); //跟rw index有关

        System.out.println(byteBuf);
        System.out.println(byteBuf.readableBytes());
        System.out.println(byteBuf.toString(0, content.length(), StandardCharsets.UTF_8));


        byteBuf.release();
    }

    public static void allocNormal() {
        // 最终分配的是 DirectByteBuffer(16MB,也就是Chunk的总大小) 中的一小段，byteBuf中的memoryAddress为此段的开始地址
        //offset = 0
        //length = 9216
        //maxLength = 16384
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(9 * KB);
        System.out.println(byteBuf);
        System.out.println(byteBuf.memoryAddress());
        //offset = 16384
        //length = 9216
        //maxLength = 16384
        byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(9 * KB);
        System.out.println(byteBuf);
        System.out.println(byteBuf.memoryAddress());
        //offset = 1048576
        //length = 1048576
        //maxLength = 1048576
        byteBuf.writeBytes(new byte[1024* KB]); //自动realloc
        System.out.println(byteBuf);
        System.out.println(byteBuf.memoryAddress());
        byteBuf.release();
    }

    public static void allocSmall() {
        // 16MB大小的Chunk的完全二叉树叶子节点id最大2048最小1024
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(32);
        System.out.println(byteBuf);
        System.out.println(byteBuf.memoryAddress());

        byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(32);
        System.out.println(byteBuf);
        System.out.println(byteBuf.memoryAddress());

        // 放回Chunk的MemoryRegionCache里(相当于free list)，避免Chunk的已分配空间出现空洞
        byteBuf.release();
    }

    public static void allocRunOut() {
        long memStart = 0;
        long memEnd = 16 * KB * KB;
        long capacity = 0;
        int count = 0;
        long pre = 0;
        Queue<ByteBuf> queue = new LinkedList<>();
        // 16 mb 可以分配 8 kb 节点 2048个，第2049个的内存起始地址等于memEnd
        // 如果绝大部分都是大对象，只有个别small和tiny的对象，会造成内存浪费
        for (int i = 0; i < 2050; i++) {
            int initialCapacity = 16 << i;
            if (initialCapacity > 8 * KB || i > 20) {
                initialCapacity = 8 * KB;
            }
            ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(initialCapacity);
            queue.add(byteBuf);
            System.out.println(byteBuf);
            System.out.println(byteBuf.memoryAddress() + " " + i);


            if (i == 0) {
                memStart = byteBuf.memoryAddress();
                memEnd += byteBuf.memoryAddress();
            } else {
                int val = (int)(byteBuf.memoryAddress() - pre) / (8 * KB);
                System.out.println(val);
                count += val;
            }
            pre = byteBuf.memoryAddress();
        }

        // 又分配回之前的tiny节点
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(16);
        queue.add(byteBuf);
        System.out.println(byteBuf);
        System.out.println(byteBuf.memoryAddress());

        System.out.println("chunk:");
        System.out.println(memStart);
        System.out.println(memEnd);
        System.out.println(capacity);
        System.out.println(count);
    }
}
