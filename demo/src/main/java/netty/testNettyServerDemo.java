package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * Created by johnny.ly on 2016/4/25.
 */
public class testNettyServerDemo {
    public static void main(String args[]) throws Exception{
        NioEventLoopGroup boss = new NioEventLoopGroup(0,new NamedThreadFactory("BOSS-THREAD",false));
        NioEventLoopGroup work = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2,
                new NamedThreadFactory("WORK-THREAD"));
        ServerBootstrap server = new ServerBootstrap();
        server.group(boss,work)
                .channel(NioServerSocketChannel.class)//
                        // .option(ChannelOption.SO_BACKLOG,
                        // SystemPropertyUtil.getInt("hsf.backlog", 100000))//
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)//
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)//
                .childOption(ChannelOption.TCP_NODELAY, true)//
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline channelPipeline = ch.pipeline();
                        channelPipeline.addLast("encode",new StringEncoder());
                        channelPipeline.addLast("decode",new StringDecoder());
                        channelPipeline.addLast("handler", new ServerHandler());
                    }
                });
        ChannelFuture cf = server.bind(new InetSocketAddress(8088)).sync();
        if (cf.isSuccess()) {
            System.out.println("Server started http transport, while listen at: " + 8088);
            return;
        }
    }
}

/**
 *  inbound, outbound use nio group thread(io thread)
 *
* */