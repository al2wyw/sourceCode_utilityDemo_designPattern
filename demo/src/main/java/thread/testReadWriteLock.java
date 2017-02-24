package thread;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/2/24
 * Time: 15:17
 * Desc:
 * 写锁上锁成功： 没有任何其他线程获得读写锁 或 本线程获得写锁但未获得读锁
 * 读锁上锁成功： 没有任何其他线程获得写锁 或 本线程获得写锁
 */
public class testReadWriteLock {
    public static void main(String[] args) throws Exception{
        final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        /*new Thread(){
            @Override
            public void run() {
                if(rwLock.readLock().tryLock()){
                    System.out.println("read");
                    if(rwLock.writeLock().tryLock()){
                        System.out.println("write");
                    }
                }

            }
        }.start();*/

        new Thread(){
            @Override
            public void run() {
                if(rwLock.writeLock().tryLock()){
                    System.out.println("write");
                    if(rwLock.readLock().tryLock()){
                        System.out.println("read");
                    }
                }

            }
        }.start();
    }
}
