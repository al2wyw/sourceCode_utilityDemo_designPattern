package netty.codec;

import java.nio.ByteBuffer;

public interface MessageEncoder<T> {

    /**
     * encode an object to byte array
     * @param object an object
     * @return a byte array
     * */
    ByteBuffer encode(T object);
}
