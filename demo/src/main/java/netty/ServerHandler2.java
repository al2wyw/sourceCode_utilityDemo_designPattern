package netty;

import io.netty.channel.*;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/8/10
 * Time: 9:34
 * Desc:
 */
public class ServerHandler2 extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("ServerHandler2 receive msg " + msg);
        ChannelFuture future = ctx.writeAndFlush("new test" + System.lineSeparator());
        future.addListener(new ChannelFutureListener(){
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                int i = 0;
                System.out.println("ServerHandler2 send successfully " + i);
            }
        });
    }
}
