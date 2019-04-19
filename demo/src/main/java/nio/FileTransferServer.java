package nio;

import org.apache.poi.util.IOUtils;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/4/18
 * Time: 17:27
 * Desc:
 */
public class FileTransferServer implements Runnable {

    private static final int KB = 1024*1024;
    private static final int MB = 1024*1024*1024;

    private Socket s;

    public FileTransferServer(Socket t) {
        s = t;
    }

    @Override
    public void run() {
        try {
            System.out.println("Connection is established!");
            InputStream din = s.getInputStream();
            System.out.println("din is established!");
            byte[] buffer = new byte[KB];
            int i = IOUtils.readFully(din, buffer);
            System.out.println("din read " + i);

            IOUtils.closeQuietly(din);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            while (true) {
                Socket s = serverSocket.accept();
                FileTransferServer st = new FileTransferServer(s);
                Thread t = new Thread(st);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
