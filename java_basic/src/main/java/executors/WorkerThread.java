/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package executors;

/**
 *
 * @author Administrator
 */
public class WorkerThread extends Thread {

    private final Channel channel;

    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    public void run() {
        while (true) {
            Runnable request = channel.takeRequest();//取出请求  
            (request).run();//处理请求   
        }
    }
}
