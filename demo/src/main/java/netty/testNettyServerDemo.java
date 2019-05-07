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
 * Socket的input,output stream在调用close时会调用Socket的close方法
 * Socket的shutdownInput,output(即使都调用了)都不会调用Socket的close方法
 * shutdownOutput后socket处于半关闭(fin_wait2,可以读不可写)，另一端进行read会返回-1，但可以正常写，ALLOW_HALF_CLOSURE支持半关闭，在read到-1时不马上调用close(与TCP半关闭一致)
 * Socket的close会关闭input,output stream和channel(不是所有的Socket都有channel，必须是来自channel的socket)
 * 服务器连接异常，一般就是两种情况: time_wait是主动发起关闭的一端(对方是closed)，通过更改tcp参数可以优化(减少fin_wait时间，reuse address等等)，close_wait是被动关闭的一端(对方是fin_wait2)，是代码逻辑出错导致没有关闭
 * 三次握手的必要性: 防止长时间滞留网路的sync报文(已超过sync_timeout)再次建立连接。 深层次原因: 需要三次握手来使得双方可以确认双向链路的可达性(实质就是双方交换确认对方的seq，并初始化窗口)
 * 四次挥手的必要性: 双工链路，可以支持一方先关闭，另一方传输完毕后再关闭
 * time_wait 2MSL的必要性: 避免最后一个ack包丢失后，可以重新发起fin包； 等待长时间滞留网路的数据包失效(与三次握手相似)
 * 流量控制: 滑动接收窗口控制流量(动态实时调整窗口大小): seq 是发送的字节流的开始index， ack 是接收到的字节流的结尾index + 1
 * 网络拥塞控制: 拥塞窗口控制网络拥塞: 慢开始和拥塞避免； 快重传和快恢复
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
                .childOption(ChannelOption.TCP_NODELAY, true)
                //TCP_NODELAY set true to disable Nagle's algorithm,
                //Nagle will wait until the previous packet ack received(buffer the packet), DelayedAcknowledgment will buffer ack to send, which lead to low performance
                .childOption(ChannelOption.SO_REUSEADDR, true)//in order to reuse time_wait socket, allow to bind the same socket more than once
                .childOption(ChannelOption.SO_BACKLOG, 100)//syn -> syn queue -> ack -> accepted queue -> accept, the size of accepted queue, syn queue 由tcp_max_syn_backlog设置
                .childOption(ChannelOption.SO_LINGER, 100)//wait to close the socket gracefully, packets can be all flushed out in time
                .childOption(ChannelOption.SO_TIMEOUT, 400)//the time out of receiving data
                .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)//socket不支持的参数，由netty schedule task实现(AbstractNioChannel)
                .childOption(ChannelOption.SO_KEEPALIVE, false)//tcp send keep-alive hear beat
                //tcp_keepalive_intvl,保活探测消息的发送频率,默认值为75s
                //tcp_keepalive_probes,TCP发送保活探测消息以确定连接是否已断开的次数.默认值为9
                //tcp_keepalive_time,TCP允许的持续空闲时间.默认值为7200s
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline channelPipeline = ch.pipeline();
                        channelPipeline.addLast("encode", new StringEncoder());
                        channelPipeline.addLast("lineFrame", new LineBasedFrameDecoder(500));
                        channelPipeline.addLast("decode", new StringDecoder());
                        channelPipeline.addLast("handler", new ServerHandler());
                        channelPipeline.addLast(work2, "handler2", new ServerHandler2());//work2跑ServerHandler2的逻辑，包括future的listener
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