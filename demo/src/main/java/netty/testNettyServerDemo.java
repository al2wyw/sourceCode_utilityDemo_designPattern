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
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.net.InetSocketAddress;

/**
 * Created by johnny.ly on 2016/4/25.
 * 写是channel主动写，读是selector驱动被动读
 * 服务器端先读后写不间断，客户端先写间断一段时间后读需要处理读写关联
 * Java NIO selector由 selectorProvider提供，根据不同的系统有Epoll实现， Netty的epoll实现是edgeTrigger(更高效)而不是NIO2的epoll的levelTrigger???
 * Socket的input,output stream在调用close时会调用Socket的close方法，Socket的shutdownInput,output(即使都调用了)都不会调用Socket的close方法(ALLOW_HALF_CLOSURE)
 * Socket的close会关闭input,output stream和channel
 * time_wait是主动发起关闭的一端，通过更改tcp参数可以优化，close_wait是被动关闭的一端，是代码逻辑出错导致没有关闭
 */
public class testNettyServerDemo {
    public static void main(String args[]) throws Exception{
        NioEventLoopGroup boss = new NioEventLoopGroup(0,new NamedThreadFactory("BOSS-THREAD",false));
        NioEventLoopGroup work = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2,
                new NamedThreadFactory("WORK-THREAD"));
        EventExecutorGroup work2 = new DefaultEventExecutorGroup(Runtime.getRuntime().availableProcessors() * 2,
                new NamedThreadFactory("EventExecutor-THREAD"));
        ServerBootstrap server = new ServerBootstrap();
        server.group(boss,work)
                .channel(NioServerSocketChannel.class)//
                        // .option(ChannelOption.SO_BACKLOG,
                        // SystemPropertyUtil.getInt("hsf.backlog", 100000))//
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)//
                .handler(new LoggingHandler(LogLevel.INFO))//还是使用NioServerSocketChannel的eventLoop,并没有实现主从线程池效果???
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)//
                .childOption(ChannelOption.TCP_NODELAY, true)//
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline channelPipeline = ch.pipeline();
                        channelPipeline.addLast("encode",new StringEncoder());
                        channelPipeline.addLast("lineFrame",new LineBasedFrameDecoder(500));
                        channelPipeline.addLast("decode",new StringDecoder());
                        channelPipeline.addLast("handler", new ServerHandler());
                        channelPipeline.addLast(work2,"handler2", new ServerHandler2());//work2跑ServerHandler2的逻辑，包括future的listener
                    }
                });
        ChannelFuture cf = server.bind(new InetSocketAddress(8088)).sync();
        if (cf.isSuccess()) {
            System.out.println("Server started http transport, while listen at: " + 8088);
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    //Thread.sleep(9999999);//导致server无法accept，虽然client可以connect也可以write数据过来
                }
            });
        }
        cf = server.bind(new InetSocketAddress(8080)).sync();
        if (cf.isSuccess()) {
            System.out.println("Server started http transport, while listen at: " + 8080);
        }
    }
}

/**
 *  inbound, outbound use nio group thread(io thread)
 *
* */