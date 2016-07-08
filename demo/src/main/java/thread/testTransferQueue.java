package thread;

import java.util.concurrent.*;

/**
 * Created by johnny.ly on 2016/2/4.
 */
public class testTransferQueue {
    private static ConcurrentLinkedQueue<String> log = new ConcurrentLinkedQueue<String>(){
        @Override
        public boolean add(String s) {
            return super.add(System.currentTimeMillis()+":"+s);
        }
    };

    public static void main(String args[]) throws Exception{
        testTransfer();
        //testSynchronize();//SynchronousQueue not use too much, for threadpoolexecutor only
        //SynchronousQueue are from abstractBlockingQueue from BlockingQueue, while TransferQueue from BlockingQueue
    }

    private static void testSynchronize()throws Exception{
        final SynchronousQueue<String> sync = new SynchronousQueue<String>();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    String s = sync.take();
                    log.add("子线程完成"+s+System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        sync.put("test0");//will block until consumer take
        log.add("主线程完成获取 "+System.currentTimeMillis());
        Thread.sleep(1000);
        executorService.shutdown();
        for(String a:log){
            System.out.println(a);
        }

        if(!executorService.awaitTermination(3,TimeUnit.SECONDS)){
            executorService.shutdownNow();
        }
    }

    private static void testTransfer()throws Exception{
        final TransferQueue<String> transferQueue = new LinkedTransferQueue<String>();
        transferQueue.put("test0");
        transferQueue.put("test2");
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            public void run() {
                try {
                    // 要两个take,才能wake up
                    transferQueue.transfer("test1");//put it into queue first and then wait for consumer to retrieve all of elements including this one
                    log.add("子线程完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        String test = transferQueue.take();
        log.add("主线程完成获取 " + test);
        Thread.sleep(1000);
        test = transferQueue.take();
        log.add("主线程完成获取 " + test);
        Thread.sleep(1000);
        executorService.shutdown();
        for(String a:log){
            System.out.println(a);
        }

        if(!executorService.awaitTermination(3,TimeUnit.SECONDS)){
            executorService.shutdownNow();
        }
        /**
        add
        if (!offerLast(e))
            throw new IllegalStateException("Deque full");

        offer
        lock.lock();
        try {
            return linkLast(node);
        } finally {
            lock.unlock();
        }

        put
        lock.lock();
        try {
            while (!linkLast(node))
                notFull.await();
        } finally {
            lock.unlock();
        }

        offer timeout
        lock.lockInterruptibly();
        try {
            while (!linkLast(node)) {
                if (nanos <= 0)
                    return false;
                nanos = notFull.awaitNanos(nanos);
            }
            return true;
        } finally {
            lock.unlock();
        }
         */
    }
}

