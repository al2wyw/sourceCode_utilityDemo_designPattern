package netty.client;

import com.google.common.util.concurrent.SettableFuture;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import netty.NamedThreadFactory;
import netty.proto.Req;
import netty.proto.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    /**
     *  trpc java:
     *  DefClusterInvoker.invoke
     *  invokerCache ServiceInstance(ip:port) : RpcClusterClientManager#RpcClientProxy -> DefRpcClient -> NettyTcpClientTransport -> Channels(ConnsPerAddr)
     * */
    private BlockingQueue<Channel> channels;
    private Bootstrap bootstrap;

    public boolean start(String scene, int cons) {
        bootstrap = new Bootstrap();
        NioEventLoopGroup workerGroup =
                new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(),
                        new NamedThreadFactory("Client-Worker-Thread",false));

        bootstrap.group(workerGroup)//
                .option(ChannelOption.TCP_NODELAY, true)//
                .option(ChannelOption.SO_REUSEADDR, true)//
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)//
                .option(ChannelOption.SO_KEEPALIVE, true)//
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>(){
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        ChannelPipeline channelPipeline = channel.pipeline();
                        channelPipeline.addLast("scene",new SceneHandler(scene));
                        channelPipeline.addLast("frameReader",new NettyFrameReader());
                        channelPipeline.addLast("codec",new NettyCodec());
                        channelPipeline.addLast("handler",new ClientHandler<>(scene));
                        //IdleStateHandler只是用于检测链路是否空闲，不会主动关闭链路
                    }
                });

        channels = new LinkedBlockingQueue<>(cons);
        return true;
    }

    public boolean connect(String host, int port) throws Exception {
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress(host, port)).sync();
        if (connect.isSuccess()) {
            channels.put(connect.channel());
        }
        return connect.isSuccess();
    }

    public Res sendMessage(Req message) throws Exception {
        SettableFuture<Res> future = SettableFuture.create();
        MessageWrapper<Req, Res> messageWrapper = new MessageWrapper<>(message, future);
        Channel channel = getChannel();
        try {
            channel.writeAndFlush(messageWrapper);
            logger.info("out--------------{}", " " + channel);
            return future.get(5000, TimeUnit.MILLISECONDS);
        } finally {
            channels.put(channel);
        }
    }

    private Channel getChannel() throws Exception {
        Channel channel = channels.take();
        if (!channel.isActive()) {
            return getChannel();
        }
        return channel;
    }
}
