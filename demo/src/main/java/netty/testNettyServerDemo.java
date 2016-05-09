package netty;

import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Created by johnny.ly on 2016/4/25.
 */
public class testNettyServerDemo {
    public static void main() throws Exception{
        NioEventLoopGroup boss = new NioEventLoopGroup(Exeutors,new NamedThreadFactory("BOSS-THREAD"));
    }
}
