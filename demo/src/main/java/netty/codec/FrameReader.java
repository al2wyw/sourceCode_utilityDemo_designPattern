package netty.codec;

import java.nio.ByteBuffer;

public interface FrameReader {

    /**
     * read a frame from ByteBuffer
     * @param byteBuffer a ByteBuffer
     * @return a ByteBuffer, if byteBuffer is not complete, return null
     * */
    ByteBuffer read(ByteBuffer byteBuffer);
}
