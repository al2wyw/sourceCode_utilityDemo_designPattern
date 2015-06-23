AbstractQueuedSynchronizer -> Sync -> NonfairSync
								   -> FairSync
Sync, NonfairSync and FairSync are all from ReentrantLock
ReentrantReadWriteLock has its own Sync, NonfairSync and FairSync

public final void acquire(int arg){
	if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}

final boolean acquireQueued(final Node node, int arg) {
	boolean failed = true;
	try {
	    boolean interrupted = false;
	    for (;;) {
	        final Node p = node.predecessor();
	        if (p == head && tryAcquire(arg)) {
	            setHead(node);
	            p.next = null; // help GC
	            failed = false;
	            return interrupted;
	        }
	        if (shouldParkAfterFailedAcquire(p, node) &&
	            parkAndCheckInterrupt()) //parkAndCheckInterrupt -> call pthread_mutex_lock(linux) to block current thread
	            interrupted = true;
	    }
	} finally {
	    if (failed)
	        cancelAcquire(node);
	}
}

NonfairSync  :

final void lock() {
    if (compareAndSetState(0, 1)) //set this thead as running thread and ignore the blocking queue
        setExclusiveOwnerThread(Thread.currentThread());
    else
        acquire(1);
}

protected final boolean tryAcquire(int acquires) {
    return nonfairTryAcquire(acquires);{ //nonfairTryAcquire is from Sync
    	final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0) {
            if (compareAndSetState(0, acquires)) {
                setExclusiveOwnerThread(current);
                return true;
            }
        }
        else if (current == getExclusiveOwnerThread()) {
            int nextc = c + acquires;
            if (nextc < 0) // overflow
                throw new Error("Maximum lock count exceeded");
            setState(nextc);
            return true;
        }
        return false;
    }
}


FairSync    :
final void lock() {
	acquire(1);
}

protected final boolean tryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        if (!hasQueuedPredecessors() &&
            compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0)
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}