package netty.codec;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import netty.proto.Req;
import netty.proto.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCodec implements MessageDecoder<Res>, MessageEncoder<Req> {

    private static final Logger logger = LoggerFactory.getLogger(MyCodec.class);

    public static final byte MAGIC = 0x02;

    public static final byte END = 0x03;

    @Override
    public ByteBuffer encode(Req object) {
        byte[] body = object.getReq().toByteArray();
        byte[] header = object.getHeader().toByteArray();
        byte[] sub = object.getPubPart().toByteArray();

        ByteBuffer byteBuffer = ByteBuffer.allocate(MyFrameReader.FRAME_HEAD_LENGTH + header.length + sub.length + body.length + 1);
        byteBuffer.order(ByteOrder.BIG_ENDIAN)
        .put(MAGIC)
        .putInt(header.length)
        .putInt(sub.length)
        .putInt(body.length)
        .put(header)
        .put(sub)
        .put(body)
        .put(END);
        byteBuffer.flip();
        return byteBuffer;
    }

    @Override
    public Res decode(ByteBuffer byteBuffer) {
        byte magic = byteBuffer.get();
        if (magic != MAGIC) {
            logger.error("magic not match {}", magic);
            return null;
        }

        int headLength = byteBuffer.getInt() + byteBuffer.getInt();
        int bodyLength = byteBuffer.getInt();
        byte[] bytes = new byte[bodyLength];
        byteBuffer.position(byteBuffer.position() + headLength);
        byteBuffer.get(bytes);
        byte end = byteBuffer.get();
        if (end != END) {
            logger.error("end not match {}", end);
            return null;
        }
        try {
            return Res.build(bytes);
        } catch (Exception e) {
            logger.error("decode error", e);
            return null;
        }
    }
}
