package thread;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import gc.TestFullGC;
import utils.PrintUtils;
import utils.ThreadUtils;

public class DisruptorTest {

    private static final int RING_SIZE = 32;

    public static class Event {
        private long message;

        public long getMessage() {
            return message;
        }

        public void setMessage(long message) {
            this.message = message;
        }
    }

    public static void main(String[] args) {
        Disruptor<Event> disruptor = new Disruptor<>(
                Event::new, RING_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE,
                new BlockingWaitStrategy());
        /*
        for (int i = 0; i < 2; i++) {
            disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
                PrintUtils.print("0x" +Long.toHexString(TestFullGC.addressOf(event)) + " " + event.getMessage());
            });
        }
        */
        disruptor.handleEventsWithWorkerPool((event) -> {
            PrintUtils.print("0x" +Long.toHexString(TestFullGC.addressOf(event)) + " " + event.getMessage());
        }, (event) -> {
            PrintUtils.print("0x" +Long.toHexString(TestFullGC.addressOf(event)) + " " + event.getMessage());
        });
        for (int i = 0; i < 10; i++) {
            disruptor.publishEvent(Event::setMessage);
        }

        PrintUtils.print("start disruptor");
        disruptor.start();
        ThreadUtils.sleep(10);

        PrintUtils.print("continue publishing");
        while (true)
        {
            disruptor.publishEvent(Event::setMessage);
            ThreadUtils.sleep(100);
        }

        //disruptor.shutdown();

    }
}
