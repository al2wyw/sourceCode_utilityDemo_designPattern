package thread;

import netty.NamedThreadFactory;

import java.util.concurrent.*;

/**
 * Created by johnny.ly on 2016/1/20.
 * runWorker -> w.lock() see interruptIdleWorkers -> w.tryLock() Worker的lock貌似是一种状态标识,标识此worker在run task
 * 而并不是作为线程同步, Worker.isLock()
 * Worker的超时回收交给workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS), 而不是另起一条线程来扫描处理
 */
public class testExecutorService {
    public static void main(String[] args) throws Exception{
        ExecutorService executorService = new ThreadPoolExecutor(4,4,60,TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(4), new NamedThreadFactory("Johnny", false), new ThreadPoolExecutor.AbortPolicy());
        Future res = executorService.submit(new Runnable() { //submit will not throw exception, while execute will throw
            public void run() {
                System.out.println(1 / 0);
            }
        });
        try {
            res.get();//this will throw exception
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        Runnable task = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(15000);
                }catch (Exception e){

                }
            }
        };
        Future result = submit(executorService, task);
        submit(executorService, task);
        submit(executorService, task);
        submit(executorService, task);
        submit(executorService, task);
        submit(executorService, task);
        submit(executorService, task);
        submit(executorService, task);
        submit(executorService, task);

        try {
            result.get(3, TimeUnit.SECONDS);
        }catch (TimeoutException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        Thread.sleep(1000);
        submit(executorService, task);
        System.out.println("cancel start");
        if(result.cancel(true)){
            System.out.println("cancel true");
            Thread.sleep(1000); //cancel后立马submit还是会有可能Reject,多线程就是这么恶心
            submit(executorService,task);
        }
        submit(executorService,task);//complete 2 tasks

        executorService.shutdown();//once execute/submit task, must shutdown(even if there is exception), otherwise main can not end

        /*
        new Thread(new Runnable() {
            public void run() {
                System.out.println(1 / 0);//exception will terminate thread
            }
        }).start();
        */
        System.out.println("test");
    }

    private static Future submit(ExecutorService executorService, Runnable task){
        try{
            return executorService.submit(task);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
/*
Future state:
	NEW -> COMPLETING -> NORMAL
    NEW -> COMPLETING -> EXCEPTIONAL
    NEW -> CANCELLED just setup the state, no more operations, so no cancelling state
    NEW -> INTERRUPTING -> INTERRUPTED

	!NEW -> isDone

Thread state:
	NEW,
    RUNNABLE,
	BLOCKED,
	WAITING,
	TIMED_WAITING,
	TERMINATED

ThreadPoolExecutor state:
    RUNNING -> SHUTDOWN
       On invocation of shutdown(), perhaps implicitly in finalize()
    (RUNNING or SHUTDOWN) -> STOP
       On invocation of shutdownNow(), drain queued tasks and interrupt workers
    SHUTDOWN -> TIDYING
       When both queue and pool are empty
    STOP -> TIDYING
       When pool is empty
    TIDYING -> TERMINATED
       When the terminated() hook method has completed
*/
