package netty.client;

import static netty.client.SceneHandler.SCENE_KEY;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import netty.codec.MessageDecoder;
import netty.codec.MessageEncoder;

public class NettyCodec extends ByteToMessageCodec<Object> {

    private static final Map<String, MessageDecoder<?>> DECODER_MAP = new HashMap<>();
    private static final Map<String, MessageEncoder<?>> ENCODER_MAP = new HashMap<>();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        String scene = SCENE_KEY.get();

        if (!in.isReadable()) {
            return;
        }
        ByteBuffer bb = in.nioBuffer();
        MessageDecoder decoder = DECODER_MAP.get(scene);
        Object res = decoder.decode(bb);
        if (res != null) {
            out.add(res);
            in.skipBytes(bb.limit()); // 一定要consume掉这个ByteBuf
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        String scene = SCENE_KEY.get();
        MessageEncoder encoder = ENCODER_MAP.get(scene);
        out.writeBytes(encoder.encode(msg));
    }

    public static void registerMessageDecoder(String key, MessageDecoder<?> decoder) {
        DECODER_MAP.put(key, decoder);
    }

    public static void registerMessageEncoder(String key, MessageEncoder<?> encoder) {
        ENCODER_MAP.put(key, encoder);
    }
}
