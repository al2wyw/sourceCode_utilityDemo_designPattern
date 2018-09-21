package thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/9/13
 * Time: 17:40
 * Desc:
 */
public class ScheduleMaxTest {

    public static void main(String[] args) throws Exception{
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("run max");
            }
        }, 3 , Long.MAX_VALUE, TimeUnit.SECONDS);
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("run normal");
            }
        }, 4 , 1, TimeUnit.SECONDS);
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("run normal");
            }
        }, 4 , 2, TimeUnit.SECONDS);
    }
}
