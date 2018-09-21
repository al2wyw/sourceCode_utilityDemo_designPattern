package nio;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/8/8
 * Time: 20:32
 * Desc:
 */
public class NioServerSocket extends Thread {

    private int port;
    private String address;

    public NioServerSocket(String name, int port, String address) {
        super(name);
        this.port = port;
        this.address = address;
    }

    public static void main(String[] args){
        NioServerSocket ss = new NioServerSocket("SSS",8001,"127.0.0.1");
        ss.start();
    }

    @Override
    public void run() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(address, port));

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server bind successfully");

            while (true){
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while (it.hasNext()){
                    SelectionKey sk = it.next();
                    it.remove(); //不删除的话SelectionKey还是在ready set里面，下次还会被select出来
                    try {
                        if (sk.isAcceptable()) {
                            ServerSocketChannel ssc = (ServerSocketChannel) sk.channel();
                            SocketChannel sc = ssc.accept();
                            if (sc.isConnected()) {
                                Socket s = sc.socket();
                                System.out.println("server accept successfully: " + s.getLocalAddress() + " " + s.getInetAddress());
                                sc.configureBlocking(false);
                                sc.register(selector, SelectionKey.OP_READ);
                            }
                        }
                        if (sk.isReadable()) {
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
                            buffer.clear();
                            buffer.put("answer".getBytes());
                            buffer.flip();
                            sc.write(buffer);
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
