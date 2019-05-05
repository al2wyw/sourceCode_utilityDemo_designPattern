package thread;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *  write lock can level down to read lock (read lock can not level up to write lock):
 *  1. get write lock
 *  2. do write operations
 *  3. get read lock
 *  4. release write lock
 *  5. do read operations
 *
 * Non-fare:
 *  1. read lock: no write lock held by other threads; no write request from other threads
 *  2. write lock: no lock held by other threads;  no read lock held by current thread (ignore all requests from other threads)
 * Fare:
 *  1. read lock: no write lock held by other threads; no request from other threads
 *  2. write lock: no lock held by other threads; no read lock held by current thread ; no request from other threads
 */
public class testReadWriteLockWithLevelUp {

	public static void main(String[] args) {
		/*
		ReadWriteLock lock = new ReentrantReadWriteLock();
		lock.readLock().lock();
		System.out.println("get the read lock");
		//can not work, but exchange the readlock and writelock, it works
		lock.writeLock().lock();
		System.out.println("get the write lock");
		lock.writeLock().unlock();
		System.out.println("release the write lock");
		lock.readLock().unlock();
		System.out.println("release the read lock");
		*/
		//testReadReentrantlyBlockWrite();
		testReadYieldWrite();
		//testWriteIgnoreAll();
	}

	private static void testReadReentrantlyBlockWrite(){
		ReadWriteLock lock = new ReentrantReadWriteLock();
		// read -> write -> read
		lock.readLock().lock();
		WriteThread w = new WriteThread(lock);
		ReadThread r = new ReadThread(lock);
		w.start();
		r.start();
		try {
			Thread.sleep(1000);
			System.out.println("main read lock start");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lock.readLock().lock();
		System.out.println("main read lock again");
		try {
			Thread.sleep(1000);
			System.out.println("main read lock start");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lock.readLock().lock();
		System.out.println("main read lock again");
		try {
			Thread.sleep(1000);
			System.out.println("main read lock start");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lock.readLock().unlock();
		lock.readLock().unlock();
		lock.readLock().unlock();
		System.out.println("main read lock release");
	}

	private static void testReadYieldWrite(){
		ReadWriteLock lock = new ReentrantReadWriteLock();
		// read -> write -> read
		lock.readLock().lock();
		WriteThread w = new WriteThread(lock);
		ReadThread r = new ReadThread(lock);
		w.start();
		r.start();
		try {
			Thread.sleep(1000);
			System.out.println("main read lock start");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lock.readLock().unlock();
		lock.readLock().lock();
		System.out.println("main read lock again");
		lock.readLock().unlock();
		System.out.println("main read lock release");
	}

	private static void testWriteIgnoreAll(){
		ReadWriteLock lock = new ReentrantReadWriteLock();

		// write lock ignore the pending requests from other thread
		lock.writeLock().lock();
		new ReadThread(lock).start();
		new ReadThread(lock).start();
		new WriteThread(lock).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main lock start");
		lock.writeLock().unlock();

		lock.writeLock().lock();
		System.out.println("main lock again");
		lock.writeLock().unlock();

		lock.writeLock().lock();
		System.out.println("main lock again");
		lock.writeLock().unlock();

		System.out.println("main locks release");
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
