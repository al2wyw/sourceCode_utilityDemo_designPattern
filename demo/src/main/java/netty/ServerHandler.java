package netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import utils.ThreadUtils;

import java.util.concurrent.*;

/**
 * Created by johnny.ly on 2016/5/9.
 */
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private ExecutorService pool = Executors.newFixedThreadPool(10,new NamedThreadFactory("BIZ-THREAD"));
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        ThreadUtils.printThreadName("in", s);
        final String t = s;
        final Channel channel = channelHandlerContext.channel();
        //runnable就够了,future没什么用
        FutureTask<Void> future = new FutureTask<Void>(new Callable<Void>() {
            public Void call() throws Exception {
                ThreadUtils.sleep(3000);
                ThreadUtils.printThreadName("in", t);
                channel.writeAndFlush(t + System.currentTimeMillis());
                return null;
            }
        });
        channelHandlerContext.fireChannelRead(s);
        pool.submit(future);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(ctx.channel().unsafe().remoteAddress());
    }
}
