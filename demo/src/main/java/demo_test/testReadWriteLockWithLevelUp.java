package demo_test;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 1. read lock: no write lock held by other threads; no write request from other threads or write request from current thread 
 * 2. write lock: no lock held by other threads; no request from other threads
 */
public class testReadWriteLockWithLevelUp {

	public static void main(String[] args) {

		ReadWriteLock lock = new ReentrantReadWriteLock();
		/*lock.readLock().lock();
		System.out.println("get the read lock");
		//can not work, but exchange the readlock and writelock, it works
		lock.writeLock().lock();
		System.out.println("get the write lock");
		lock.writeLock().unlock();
		System.out.println("release the write lock");
		lock.readLock().unlock();  
		System.out.println("release the read lock");*/
		
		/*
		// read -> write -> read
		lock.readLock().lock();
		WriteThread w = new WriteThread(lock);
		ReadThread r = new ReadThread(lock);
		w.start();
		r.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main lock done");
		lock.readLock().unlock();
		*/
		
		// write -> read -> read -> write
		lock.writeLock().lock();
		new ReadThread(lock).start();
		new ReadThread(lock).start();
		new WriteThread(lock).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main lock done");
		lock.writeLock().unlock();
	}

}

class WriteThread extends Thread{

	private ReadWriteLock lock;
	
	public WriteThread(ReadWriteLock lock){
		this.lock = lock;
	}
	
	@Override
	public void run() {
		try{
			Thread.sleep(700); //change to 500 make it run first
			System.out.println("wirte lock start!!!");
			lock.writeLock().lock();
			System.out.println("wirte lock get");
			Thread.sleep(500);
			System.out.println("wirte lock done");
		}catch(Exception e){
			
		}finally{
			lock.writeLock().unlock();
		}
	}
	
}

class ReadThread extends Thread{

	private ReadWriteLock lock;
	
	public ReadThread(ReadWriteLock lock){
		this.lock = lock;
	}
	
	@Override
	public void run() {
		try{
			Thread.sleep(500); //change to 700 make it run later
			System.out.println("read lock start!!!");
			lock.readLock().lock();
			System.out.println("read lock get");
			Thread.sleep(500);
			System.out.println("read lock done");
		}catch(Exception e){
			
		}finally{
			lock.readLock().unlock();
		}
	}
	
}
