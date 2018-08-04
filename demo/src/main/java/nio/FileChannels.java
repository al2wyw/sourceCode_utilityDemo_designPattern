package nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/6/13
 * Time: 11:49
 * Desc:
 */
public class FileChannels {

    public static void main(String[] args) throws Exception{

        mappedFileChannelLock();

        // Create a temp file and get a channel connected to it
        File tempFile = File.createTempFile ("mmaptest", null);
        RandomAccessFile file = new RandomAccessFile (tempFile, "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer temp = ByteBuffer.allocate (100);
        // Put something in the file, starting at location 0
        temp.put ("This is the file content".getBytes( ));
        temp.flip( );
        channel.write (temp, 0);
        // Put something else in the file, starting at location 8192.
        // 8192 is 8 KB, almost certainly a different memory/FS page.
        // This may cause a file hole, depending on the
        // filesystem page size.
        temp.clear( );
        temp.put ("This is more file content".getBytes( ));
        temp.flip( );
        channel.write (temp, 8192);
        // Create three types of mappings to the same file
        MappedByteBuffer ro = channel.map (
                FileChannel.MapMode.READ_ONLY, 0, channel.size( ));
        MappedByteBuffer rw = channel.map (
                FileChannel.MapMode.READ_WRITE, 0, channel.size( ));
        MappedByteBuffer cow = channel.map (
                FileChannel.MapMode.PRIVATE, 0, channel.size( ));
        // the buffer states before any modifications
        System.out.println ("Begin");
        showBuffers (ro, rw, cow);
        // Modify the copy-on-write buffer
        cow.position (8);
        cow.put ("COW".getBytes( ));
        System.out.println ("Change to COW buffer");
        showBuffers (ro, rw, cow);
        // Modify the read/write buffer92
        rw.position (9);
        rw.put (" R/W ".getBytes( ));
        rw.position (8194);
        rw.put (" R/W ".getBytes( ));
        rw.force( );
        System.out.println ("Change to R/W buffer");
        showBuffers (ro, rw, cow);
        // Write to the file through the channel; hit both pages
        temp.clear( );
        temp.put ("Channel write ".getBytes( ));
        temp.flip( );
        channel.write (temp, 0);
        temp.rewind( );
        channel.write (temp, 8202);
        System.out.println ("Write on channel");
        showBuffers (ro, rw, cow);
        // Modify the copy-on-write buffer again
        cow.position (8207);
        cow.put (" COW2 ".getBytes( ));
        System.out.println ("Second change to COW buffer");
        showBuffers (ro, rw, cow);
        // Modify the read/write buffer
        rw.position (0);
        rw.put (" R/W2 ".getBytes( ));
        rw.position (8210);
        rw.put (" R/W2 ".getBytes( ));
        rw.force( );
        System.out.println ("Second change to R/W buffer");
        showBuffers (ro, rw, cow);
        // cleanup
        channel.close( );
        file.close( );
        tempFile.delete( );
    }

    public static void showBuffers (ByteBuffer ro, ByteBuffer rw, ByteBuffer cow) throws Exception{
        dumpBuffer ("R/O", ro);
        dumpBuffer ("R/W", rw);
        dumpBuffer ("COW", cow);
        System.out.println ("");
    }

    public static void dumpBuffer (String prefix, ByteBuffer buffer) throws Exception {
        System.out.print(prefix + ": '");
        int nulls = 0;
        int limit = buffer.limit( );
        for (int i = 0; i < limit; i++) {
            char c = (char) buffer.get (i);
            if (c == '\u0000') {
                nulls++;
                continue;
            }
            if (nulls != 0) {
                System.out.print ("|[" + nulls
                        + " nulls]|");
                nulls = 0;
            }
            System.out.print (c);
        }
        System.out.println ("'");
    }

    public static void mappedFileChannelLock() throws Exception{
        RandomAccessFile afile = null;
        RandomAccessFile bfile = null;
        FileChannel fc = null;
        FileChannel fcb = null;
        try {
            afile = new RandomAccessFile("hello.txt", "rw");
            fc = afile.getChannel();
            long length = fc.size();
            FileLock fileLock = fc.tryLock(0, length, true);//true共享锁 false 独占锁 从开始 锁定全部内容 如果获取不到锁会返回null
            if(null != fileLock) {
                MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, length);
                byte[] fbo = new byte[(int) length];
                mbb.get(fbo);
                System.out.println(new String(fbo, "UTF-8"));
                fileLock.release();
                bfile = new RandomAccessFile("hehe.txt", "rw");
                fcb = bfile.getChannel();
                fileLock = fcb.tryLock(0, length, false);
                MappedByteBuffer mbbb = fcb.map(FileChannel.MapMode.READ_WRITE, 0, length);

                /*for (int i = 0; i < length; i++) {
                    mbbb.put(i, fbo[i]);
                }*/
                mbb.position(0);
                mbbb.put(mbb);//什么时候刷盘?
                //mbbb.force();
                fileLock.release();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fc.close();
                fcb.close();
                afile.close();
                bfile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void fileTransferByNormal() {
        try {
            RandomAccessFile afile = new RandomAccessFile("hello.txt", "rw");
            RandomAccessFile bfile = new RandomAccessFile("hehe.txt", "rw");
            FileChannel ac = afile.getChannel();
            FileChannel bc = bfile.getChannel();

            ByteBuffer bf = ByteBuffer.allocateDirect(16 * 1024);
            while (ac.read(bf) != -1) {
                bf.flip();
                while (bf.hasRemaining()) {//确保写完
                    bc.write(bf);
                }
                bf.clear();
            }
            ac.close();
            afile.close();
            bc.close();
            bfile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
