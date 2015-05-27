/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package executors;
import java.util.*;
/**
 *
 * @author Administrator
 */
public class Channel {

    private static final int MAX_REQUEST = 100;
    private final ArrayList<Runnable> requestQueue;//存放请求的队列     
    private int count; // Request的数量   
    private final WorkerThread[] threadPool;

    public Channel(int threads) {
        requestQueue = new ArrayList<Runnable>();
        this.count = 0;
        threadPool = new WorkerThread[threads];
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i] = new WorkerThread("Worker-" + i, this);//生成线程池中的线程   
        }
    }

    public void startWorkers() {
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i].start();//启动线程池中的线程   
        }
    }

    public synchronized void putRequest(Runnable request) {//向队列中存入请求   
        while (count >= MAX_REQUEST) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        requestQueue.add(request);
        count++;
        notifyAll();
    }

    public synchronized Runnable takeRequest() {//从队列取出请求   
        while (count <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        Runnable request = requestQueue.remove(0);
        count--;
        notifyAll();
        return request;
    }
}
