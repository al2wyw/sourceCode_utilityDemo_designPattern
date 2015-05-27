/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package executors;

/**
 *
 * @author Administrator
 */
public class Main {
    public static void main(String[] args) {
        Channel channel = new Channel(5);   // 工人线程的數量,即线程池内的线程数目
        channel.startWorkers();//启动线程池内的线程
        channel.putRequest(new ClientThread("Alice"));
        channel.putRequest(new ClientThread("Bobby"));
        channel.putRequest(new ClientThread("Chris"));
        channel.putRequest(new ClientThread("Chris"));
        channel.putRequest(new ClientThread("Chris"));
        channel.putRequest(new ClientThread("Chris"));
        channel.putRequest(new ClientThread("Chris"));
        channel.putRequest(new ClientThread("Chris"));
        channel.putRequest(new ClientThread("Chris"));
        /*new ClientThread("Alice", channel).start();//发送请求的线程，相当于向队列加入请求
        new ClientThread("Bobby", channel).start();
        new ClientThread("Chris", channel).start();*/
        
    }
}

