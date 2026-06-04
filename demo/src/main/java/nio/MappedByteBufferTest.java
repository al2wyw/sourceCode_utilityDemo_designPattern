package nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

// jdk update incompatibility javac 17 -> java 8 nsm error MappedByteBuffer.position(100)
// check the major page fault
public class MappedByteBufferTest {

    public static void main(String[] args) throws Exception {

        String filePath = args[0];
        boolean shouldStop = args.length > 1 && Boolean.parseBoolean(args[1]);
        System.out.println(filePath);

        try (RandomAccessFile file = new RandomAccessFile(filePath, "r");
             FileChannel channel = file.getChannel()) {
            System.out.println(channel.size());
            // 将文件从0开始全部映射到内存
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            int count = 0;
            long ret = 0;
            for (int i = 0; i < buffer.limit(); i++) {
                byte b = buffer.get(i);
                ret += b;
                count++;
            }
            System.out.println(count);
            System.out.println(ret);
        }

        // 防止进程退出，查看进程内存映射
        // cat /proc/pid/maps
        if (shouldStop) {
            Thread.sleep(1000000000);
        }

    }
}
