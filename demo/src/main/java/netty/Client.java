package netty;

import com.google.common.collect.Lists;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import utils.ThreadUtils;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/3/27
 * Time: 11:28
 * Desc:
 */
public class Client {

    private static final List<Channel> channels = Lists.newArrayList();

    public static void main(String args[]) throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup workerGroup =
                new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2,
                        new NamedThreadFactory("Client-Worker-Thread", false));
        bootstrap.group(workerGroup)//
                .option(ChannelOption.TCP_NODELAY, true)//
                .option(ChannelOption.SO_REUSEADDR, true)//
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)//
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        ChannelPipeline channelPipeline = channel.pipeline();
                        channelPipeline.addLast("encode", new StringEncoder());
                        channelPipeline.addLast("lineFrame", new LineBasedFrameDecoder(500));
                        channelPipeline.addLast("decode", new StringDecoder());
                        channelPipeline.addLast("handler", new ClientHandler());
                    }
                });

        for(int i = 0; i < 10; i++) {
            ChannelFuture connect = bootstrap.connect(new InetSocketAddress(12345)).sync();
            if (connect.isSuccess()) {
                channels.add(connect.channel());
            }
        }
        System.out.println("client open channels: " + channels.size());

        for (int i = 0; i < 10; i++) {
            Message mes = new Message();
            mes.setMsg("test" + i + System.lineSeparator());
            mes.setUuid(UUID.randomUUID().toString());
            Channel channel = channels.get(i);
            ChannelFuture future = channel.writeAndFlush(mes);
            ThreadUtils.printThreadName("out", mes.toString() + " " + channel);
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    System.out.println("flush test to server");
                }
            });
        }
        //不能在ide里运行以下代码
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                channels.stream().forEach(ch -> {
                    System.out.println("close channel " + ch);
                    ch.close();
                });
            }
        });
    }
}
