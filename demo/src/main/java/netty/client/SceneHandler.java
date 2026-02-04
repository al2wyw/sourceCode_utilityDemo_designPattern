package netty.client;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class SceneHandler extends ChannelDuplexHandler {

    private static final String SCENE = "scene";

    private final String value;

    public static final ThreadLocal<String> SCENE_KEY = new ThreadLocal<>();

    public SceneHandler(String value) {
        this.value = value;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SCENE_KEY.set(value);
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SCENE_KEY.set(value);
        super.channelInactive(ctx);
    }
}
