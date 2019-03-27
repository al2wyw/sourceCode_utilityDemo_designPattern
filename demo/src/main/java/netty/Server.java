package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/3/27
 * Time: 11:24
 * Desc:
 */
public class Server {
    public static void main(String args[]) throws Exception {
        NioEventLoopGroup boss = new NioEventLoopGroup(0, new NamedThreadFactory("BOSS-THREAD", false));
        NioEventLoopGroup work = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2,
                new NamedThreadFactory("WORK-THREAD"));
        ServerBootstrap server = new ServerBootstrap();
        server.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_TIMEOUT, 3000)
                .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline channelPipeline = ch.pipeline();
                        channelPipeline.addLast("encode", new StringEncoder());
                        channelPipeline.addLast("lineFrame", new LineBasedFrameDecoder(500));
                        channelPipeline.addLast("decode", new StringDecoder());
                        channelPipeline.addLast("handler", new ServerHandler());
                    }
                });
        ChannelFuture cf = server.bind(new InetSocketAddress(12345)).sync();
        if (cf.isSuccess()) {
            System.out.println("Server started http transport, while listen at: " + 12345);
        }
    }
}
