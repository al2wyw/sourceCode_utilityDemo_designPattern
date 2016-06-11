package netty;

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
        FutureTask<String> future = new FutureTask<String>(new Callable<String>() {
            public String call() throws Exception {
                ThreadUtils.printThreadName("in", t);
                return t + System.currentTimeMillis();
            }
        });
        pool.submit(future);
        channelHandlerContext.writeAndFlush(future.get());
    }
}
