package nio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/8/8
 * Time: 23:07
 * Desc:
 */
public class NioClientSocket extends Thread {

    private int port;
    private String address;
    private int loop = 10;

    public NioClientSocket(String name, int port, String address) {
        super(name);
        this.port = port;
        this.address = address;
    }

    public static void main(String[] args) throws Exception{
        for(int i=0; i< 10 ; i++) {
            NioClientSocket nioClientSocket = new NioClientSocket("CCC", 8001, "127.0.0.1");
            nioClientSocket.start();
        }
    }

    private static void socket() throws Exception{
        Socket s = new Socket();
        s.connect(new InetSocketAddress("127.0.0.1",8001));
        System.out.println("local: " + s.getLocalAddress());
        OutputStream output = s.getOutputStream();
        output.write("test for a new day".getBytes());
        output.flush();
        InputStream input = s.getInputStream();
        byte[] buffer = new byte[1024];
        int i = input.read(buffer);
        System.out.println(new String(buffer,0,i));
        Thread.sleep(10000);
        s.close();
    }

    private void write(SelectionKey sk, SocketChannel sc, String msg){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(msg.getBytes());
        buffer.flip();
        try {
            int size = sc.write(buffer);
            System.out.println("write :" + size);
        }catch (Exception e){
            if(sk!=null){
                sk.cancel();
                if(sk.channel()!=null){
                    try {
                        sk.channel().close();
                    }catch (Exception o){
                        o.printStackTrace();
                    }
                }
                System.out.println("Exception close sk");
            }
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            Selector selector = Selector.open();
            SocketChannel scc = SocketChannel.open();
            scc.configureBlocking(false);
            scc.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
            scc.connect(new InetSocketAddress(address,port));

            int l = 0;
            while (true){
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey sk = it.next();
                    it.remove();
                    try {
                        if(sk.isConnectable()){
                            if (!sk.isValid()) {
                                System.out.println("connect invalid");
                                sk.channel().close();
                                continue;
                            }
                            SocketChannel sc = (SocketChannel) sk.channel();
                            if(sc.finishConnect()){
                                write(sk, sc,"test for new day");
                                System.out.println("connect successfully");
                            }else{
                                System.out.println("connect failed");
                                sk.cancel();
                            }
                        }
                        if(sk.isReadable()){
                            if (!sk.isValid()) {
                                System.out.println("invalid");
                                sk.channel().close();
                                continue;
                            }
                            SocketChannel sc = (SocketChannel) sk.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            int size = sc.read(buffer);
                            if(size == -1){
                                sk.cancel();
                                sc.close();
                                System.out.println("sk is closed");
                                continue;
                            }
                            buffer.flip();
                            byte[] bytes = new byte[size];
                            System.out.println("read bytes from channel: " + size);
                            buffer.get(bytes);
                            String s = new String(bytes, "UTF-8");
                            System.out.println("read from channel: " + s);
                            Thread.sleep(1000);
                            if(l < loop){
                                l++;
                                write(sk, sc, "test for new day again");
                            }else{
                                sk.cancel();
                                sc.close();
                            }
                        }
                    }catch (Exception e){
                        if(sk!=null){
                            sk.cancel();
                            if(sk.channel()!=null){
                                sk.channel().close();
                            }
                            System.out.println("Exception close sk");
                        }
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
