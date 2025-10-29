package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import java.nio.charset.StandardCharsets;

public class TestByteBuf {

    public static void main(String[] args) throws Exception {

        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(1024, 1024);

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
}
