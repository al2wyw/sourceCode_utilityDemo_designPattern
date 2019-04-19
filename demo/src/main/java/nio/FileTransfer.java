package nio;

import org.apache.poi.util.IOUtils;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/4/18
 * Time: 17:16
 * Desc:
 */
public class FileTransfer {

    private static final String FILE = "/D:/Desktop.7z";

    public static void main(String[] args){
        //fileChannelCopy();
        fileChannelZeroCopy();
    }

    private static void fileChannelCopy(){
        Socket s;
        try {
            s = new Socket(InetAddress.getByName("localhost"), 7777);
            OutputStream outputStream = s.getOutputStream();
            FileInputStream fin = new FileInputStream(FILE);
            FileChannel fileChannel = fin.getChannel();
            fileChannel.transferTo(0, fileChannel.size(), Channels.newChannel(outputStream));//this is not zero copy

            //ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
            //fileChannel.write(byteBuffer);

            IOUtils.closeQuietly(fin);
            IOUtils.closeQuietly(outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void fileChannelZeroCopy(){
        try{
            SocketChannel scc = SocketChannel.open();
            scc.connect(new InetSocketAddress(InetAddress.getByName("192.168.100.104"), 7777));
            //once be registered with selector, can not change blocking any more
            scc.configureBlocking(true);//plus -Djdk.nio.enableFastFileTransfer=true

            System.out.println("connect successfully " + scc.isConnected());
            FileInputStream fin = new FileInputStream(FILE);
            FileChannel fileChannel = fin.getChannel();
            /**
             java.lang.Thread.State: RUNNABLE
             at sun.nio.ch.FileChannelImpl.transferTo0(Native Method)
             at sun.nio.ch.FileChannelImpl.transferToDirectlyInternal(FileChannelImpl.java:428)
             at sun.nio.ch.FileChannelImpl.transferToDirectly(FileChannelImpl.java:486)
             - locked <0x000000076bb1f758> (a java.lang.Object)
             at sun.nio.ch.FileChannelImpl.transferTo(FileChannelImpl.java:608)
             at nio.FileTransfer.fileChannelZeroCopy(FileTransfer.java:60)
             at nio.FileTransfer.main(FileTransfer.java:28)
             * */
            fileChannel.transferTo(0,fileChannel.size(),scc);
            IOUtils.closeQuietly(fin);
            scc.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
