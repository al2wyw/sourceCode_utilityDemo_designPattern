package thread;

import java.util.concurrent.Exchanger;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/7/24
 * Time: 11:00
 * Desc:
 */
public class ExchangerTest {

    public static void main(String[] args) throws Exception{
        Exchanger<ExObj> exchanger = new Exchanger<>();//多个线程时producer,consumer互相乱窜
        consumer c1 = new consumer(exchanger);
        consumer c2 = new consumer(exchanger);
        producer p1 = new producer(exchanger);
        producer p2 = new producer(exchanger);
        c1.start();
        c2.start();
        p1.start();
        p2.start();
    }

    private static class consumer extends Thread{

        private Exchanger<ExObj> exchanger;

        public consumer(Exchanger<ExObj> exchanger){
            super();
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            try {
                ExObj pro = new ExObj();
                pro.setI(-1);
                pro = exchanger.exchange(pro);
                System.out.println("consumer 1 "+ pro.getI());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static class producer extends Thread{

        private Exchanger<ExObj> exchanger;

        public producer(Exchanger<ExObj> exchanger){
            super();
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            ExObj pro = new ExObj();
            pro.setI(1);
            try {
                pro = exchanger.exchange(pro);
                System.out.println("producer -1 "+ pro.getI());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static class ExObj{
        private int i = 0;

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }
    }
}
