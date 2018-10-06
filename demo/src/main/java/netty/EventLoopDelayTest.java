package netty;

import io.netty.channel.nio.NioEventLoopGroup;
import utils.ThreadUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by win10 on 2018/10/7.
 */
public class EventLoopDelayTest {

    public static void main(String args[]) throws Exception{
        NioEventLoopGroup boss = new NioEventLoopGroup(1,new NamedThreadFactory("BOSS-THREAD",false));
        testDelay(boss);//trigger not inEventLoop, turn into execute, will not delay
        boss.execute(new Runnable() {
            @Override
            public void run() {
                testDelay(boss);
                //trigger inEventLoop, select will be called later on(when the thread block on select,
                // schedule can not be called), will not delay
            }
        });
    }

    private static void testDelay(NioEventLoopGroup boss){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() +" run");
            }
        };
        int i = 0;
        while(i++ < 10) {
            System.out.println(System.currentTimeMillis());
            boss.schedule(r, 100, TimeUnit.MILLISECONDS);
            ThreadUtils.sleep(200);
        }
    }
}