package nio;

import org.apache.poi.util.IOUtils;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/4/18
 * Time: 17:16
 * Desc: 还没搞清楚
 */
public class FileTransfer {

    private static final String FILE = "/D:/Archive.7z";

    public static void main(String[] args){
        Socket s;
        try {
            s = new Socket(InetAddress.getByName("localhost"), 7777);
            OutputStream outputStream = s.getOutputStream();
            FileInputStream fin = new FileInputStream(FILE);
            FileChannel fileChannel = fin.getChannel();
            fileChannel.transferTo(0, fileChannel.size(), Channels.newChannel(outputStream));

            //ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
            //fileChannel.write(byteBuffer);

            IOUtils.closeQuietly(fin);
            IOUtils.closeQuietly(outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
