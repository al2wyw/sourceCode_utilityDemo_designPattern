package netty.client;

import static netty.client.SceneHandler.SCENE_KEY;

import com.google.common.util.concurrent.SettableFuture;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by johnny.ly on 2016/5/10.
 */
public class ClientHandler<R, T> extends ChannelDuplexHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    private final ConcurrentHashMap<Channel, MessageWrapper<R,T>> requestMap = new ConcurrentHashMap<>();

    private final String value;

    public ClientHandler(String value) {
        this.value = value;
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object s) throws Exception {
        MessageWrapper<R, T> wrapper = requestMap.get(channelHandlerContext.channel());
        if (wrapper != null) {
            SettableFuture<T> future = wrapper.getRes();
            future.set((T)s);
            requestMap.remove(channelHandlerContext.channel());
        }
        printThreadName("in", " " + channelHandlerContext.channel());
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        SCENE_KEY.set(value);
        requestMap.put(ctx.channel(), (MessageWrapper<R, T>) msg);
        printThreadName("out", " " + ctx.channel());
        ctx.writeAndFlush(((MessageWrapper<R, T>) msg).getReq(), promise);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        printThreadName("exception", " " + ctx.channel());
        logger.error("", cause);
    }

    private void printThreadName(String direct, String mess){
        logger.info("{}--------------{}", direct, mess);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        MessageWrapper<R, T> wrapper = requestMap.get(ctx.channel());
        if (wrapper != null) {
            SettableFuture<T> future = wrapper.getRes();
            future.setException(new Exception("channel inactive"));
            requestMap.remove(ctx.channel());
        }
        printThreadName("inactive", " " + ctx.channel());
    }


}
