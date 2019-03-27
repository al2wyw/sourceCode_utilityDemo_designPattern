package netty;

import com.google.common.collect.Maps;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import utils.ThreadUtils;

import java.util.Map;

/**
 * Created by johnny.ly on 2016/5/10.
 */
public class ClientHandler extends ChannelDuplexHandler {

    private Map<String, Message> requestMap = Maps.newHashMap();

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object s) throws Exception {
        String test = (String)s;
        if(test.contains("$")){//server handler2
            Message msg = new Message();
            msg.fromString((String)s);
            requestMap.get(msg.getUuid()).getAnswer().set(msg.getMsg());
        }
        ThreadUtils.printThreadName("in", test + " " + channelHandlerContext.channel());
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        Message message = (Message) msg;
        requestMap.put(message.getUuid(),message);
        ctx.write(message.toString(),promise);
    }
}
