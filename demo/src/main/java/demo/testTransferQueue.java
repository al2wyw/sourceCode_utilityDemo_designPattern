package demo;

import java.util.concurrent.*;

/**
 * Created by johnny.ly on 2016/2/4.
 */
public class testTransferQueue {
    private static CopyOnWriteArrayList<String> log = new CopyOnWriteArrayList<String>();
    public static void main(String args[]) throws Exception{
        final TransferQueue<String> transferQueue = new LinkedTransferQueue<String>();
        transferQueue.put("test0");
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            public void run() {
                try {
                    // 要两个take,才能wake up
                    transferQueue.transfer("test1");
                    log.add("子线程完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        String test = transferQueue.take();
        log.add("主线程完成获取 " + test);
        Thread.sleep(1000);
        executorService.shutdown();
        for(String a:log){
            System.out.println(a);
        }

        if(!executorService.awaitTermination(3,TimeUnit.SECONDS)){
            executorService.shutdownNow();
        }
    }
}
