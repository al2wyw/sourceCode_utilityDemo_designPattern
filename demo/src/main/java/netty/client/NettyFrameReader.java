package netty.client;

import static netty.client.SceneHandler.SCENE_KEY;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import netty.codec.FrameReader;

public class NettyFrameReader extends ByteToMessageDecoder {

    private static final Map<String, FrameReader> FRAME_READERS = new HashMap<>();

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        String scene = SCENE_KEY.get();

        if (!in.isReadable()) {
            return;
        }
        ByteBuffer bb = in.nioBuffer();
        ByteBuffer ret = null;
        try {
            ret = FRAME_READERS.get(scene).read(bb);
        } finally {
            if (ret != null) {
                out.add(in.slice(in.readerIndex(), ret.limit()).retain()); //ByteBuf作为output，后续的Decoder会对它进行特殊处理
                in.skipBytes(ret.limit()); // 一定要consume掉ByteBuf对应的内容
            }
        }
    }

    public static boolean registerFrameReader(String key, FrameReader frameReader) {
        FRAME_READERS.put(key, frameReader);
        return true;
    }
}
