package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.net.InetSocketAddress;

/**
 * Created by johnny.ly on 2016/4/27.
 */
public class testNettyClientDemo {
    public static void main(String args[]) throws Exception{
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup workerGroup =
                new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2,
                    new NamedThreadFactory("Client-Worker-Thread",false));
        bootstrap.group(workerGroup)//
                .option(ChannelOption.TCP_NODELAY, true)//
                .option(ChannelOption.SO_REUSEADDR, true)//
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)//
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>(){
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        ChannelPipeline channelPipeline = channel.pipeline();
                        channelPipeline.addLast("encode",new StringEncoder());
                        channelPipeline.addLast("decode",new StringDecoder());
                        channelPipeline.addLast("handler",new ClientHandler());
                    }
                });
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress(8088)).sync();
        if(connect.isSuccess()){
            for(int i = 0; i < 10; i++) {
                ChannelFuture future = connect.channel().writeAndFlush("test");
                future.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        System.out.println("flush test to server");
                    }
                });
            }
        }
    }
}
