package netty.codec;

import java.nio.ByteBuffer;

public interface MessageDecoder<T> {

    /**
     * decode a byte array to an object
     * @param bytes a byteBuffer contains message
     * @return an object
     * */
    T decode(ByteBuffer bytes);
}
