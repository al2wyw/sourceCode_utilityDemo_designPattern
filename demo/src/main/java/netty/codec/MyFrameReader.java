package netty.codec;

import java.nio.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyFrameReader implements FrameReader {

    private static final Logger logger = LoggerFactory.getLogger(MyFrameReader.class);

    public static final int FRAME_HEAD_LENGTH = 13;

    private static final int MAX_FRAME_LENGTH = 1024 * 1024;

    @Override
    public ByteBuffer read(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() < FRAME_HEAD_LENGTH) {
            return null;
        }

        byteBuffer.mark();
        byteBuffer.get();
        int headLength = byteBuffer.getInt() + byteBuffer.getInt();
        int bodyLength = byteBuffer.getInt();
        int totalLength = headLength + bodyLength + 1;
        if (totalLength + FRAME_HEAD_LENGTH > MAX_FRAME_LENGTH) {
            logger.error("frame length {} exceeds max {}", totalLength + FRAME_HEAD_LENGTH, MAX_FRAME_LENGTH);
            return null;
        }

        if (byteBuffer.remaining() < totalLength) {
            return null;
        }

        byteBuffer.reset();
        byteBuffer.limit(totalLength + FRAME_HEAD_LENGTH);

        return byteBuffer;
    }
}
