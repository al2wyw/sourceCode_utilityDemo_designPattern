import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentHashMap.BaseIterator;
import java.util.concurrent.ConcurrentHashMap.CollectionView;
import java.util.concurrent.ConcurrentHashMap.MapEntry;
import java.util.concurrent.ConcurrentHashMap.Traverser;
import java.util.concurrent.ConcurrentSkipListMap.Iter;
import java.util.concurrent.ConcurrentSkipListMap.Node;
import java.util.concurrent.ConcurrentSkipListMap.SubMap;

import demo_test.ConcurrentHashMap.HashEntry;
import demo_test.ConcurrentHashMap.Segment;

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

ConcurrentSkipListMap:
	//no locks are applied!!!
	//The map is sorted according to the natural ordering
	//the bulk operations are not atomic, the size method is not accurate(need traversal through whole map)
	//this class does not permit the use of null keys or values
	
	//Iterator is weak-consistent, will not throw ConcurrentModificationException(fast fail)
	final class EntryIterator extends Iter<Map.Entry<K,V>> {
	    public Map.Entry<K,V> next() {
	        Node<K,V> n = next;
	        V v = nextValue;
	        advance();
	        return new AbstractMap.SimpleImmutableEntry<K,V>(n.key, v);//Entry can not modify
	    }
	}

	abstract class Iter<T> implements Iterator<T> {
	    ...
	    
	    public void remove() {
	        Node<K,V> l = lastReturned;
	        if (l == null)
	            throw new IllegalStateException();
	        ConcurrentSkipListMap.this.remove(l.key);//can remove elements from the original map
	        lastReturned = null;
	    }
	}
	
	//The set is backed by the map, so changes to the map are reflected in the set, and vice-versa
	//not all the operations are supported, for example, add is not supported
	static final class EntrySet<K1,V1> extends AbstractSet<Map.Entry<K1,V1>> {
        final ConcurrentNavigableMap<K1, V1> m; 
        EntrySet(ConcurrentNavigableMap<K1, V1> map) {
            m = map; //map will be the ConcurrentSkipListMap
        }
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            return m.remove(e.getKey(),
                            e.getValue());
        }
        public boolean isEmpty() {
            return m.isEmpty();
        }
        public int size() {
            return m.size();
        }
        public void clear() {
            m.clear();
        }
	}

ConcurrentHashMap: (functionally similar to Hashtable) (this is 1.8 ver, 1.7 ver is the same as ConcurrentSkipListMap)
	//concurrencyLevel: The table is internally partitioned to try to permit the indicated number of concurrent updates without contention
	//retrieval operations do not entail locking, the other operations require partially locking, so retrieval may overlap with update operation
	//this class does not permit the use of null keys or values
	
	//Iterator is weak-consistent, will not throw ConcurrentModificationException(fast fail)
	static final class EntryIterator<K,V> extends BaseIterator<K,V>
        implements Iterator<Map.Entry<K,V>> {
        EntryIterator(Node<K,V>[] tab, int index, int size, int limit,
                      ConcurrentHashMap<K,V> map) {
            super(tab, index, size, limit, map);
        }
        
        ...
        
        public final Map.Entry<K,V> next() {
            Node<K,V> p;
            if ((p = next) == null)
                throw new NoSuchElementException();
            K k = p.key;
            V v = p.val;
            lastReturned = p;
            advance();
            return new MapEntry<K,V>(k, v, map);//Entry can modify
        }
	}
	
	 static class BaseIterator<K,V> extends Traverser<K,V> {
	        final ConcurrentHashMap<K,V> map;
	        Node<K,V> lastReturned;
	        BaseIterator(Node<K,V>[] tab, int size, int index, int limit,
	                    ConcurrentHashMap<K,V> map) {
	            super(tab, size, index, limit);
	            this.map = map;
	            advance();
	        }
	        
	        ...

	        public final void remove() {
	            Node<K,V> p;
	            if ((p = lastReturned) == null)
	                throw new IllegalStateException();
	            lastReturned = null;
	            map.replaceNode(p.key, null, null);//can remove elements from the original map
	        }
	    }

	//The set is backed by the map, so changes to the map are reflected in the set, and vice-versa
	static final class EntrySetView<K,V> extends CollectionView<K,V,Map.Entry<K,V>>
        implements Set<Map.Entry<K,V>>, java.io.Serializable {
        private static final long serialVersionUID = 2249069246763182397L;
        EntrySetView(ConcurrentHashMap<K,V> map) { super(map); }////map will be the ConcurrentHashMap
        ...
	}
	
	//final Segment<K,V>[] segments;
	//Segment number is power of 2, Segment slot is power of 2
	public ConcurrentHashMap(int initialCapacity,
            float loadFactor, int concurrencyLevel) {
		if (!(loadFactor > 0) || initialCapacity < 0 || concurrencyLevel <= 0)
			throw new IllegalArgumentException();
		if (concurrencyLevel > MAX_SEGMENTS)
			concurrencyLevel = MAX_SEGMENTS;
		// Find power-of-two sizes best matching arguments
		int sshift = 0;
		int ssize = 1;
		while (ssize < concurrencyLevel) {
			++sshift;
			ssize <<= 1;
		}
		this.segmentShift = 32 - sshift;
		this.segmentMask = ssize - 1;
		if (initialCapacity > MAXIMUM_CAPACITY)
			initialCapacity = MAXIMUM_CAPACITY;
		int c = initialCapacity / ssize;
		if (c * ssize < initialCapacity)
			++c;
		int cap = MIN_SEGMENT_TABLE_CAPACITY;
		while (cap < c)
			cap <<= 1;
		// create segments and segments[0]
		Segment<K,V> s0 =
		new Segment<K,V>(loadFactor, (int)(cap * loadFactor),
		            (HashEntry<K,V>[])new HashEntry[cap]);
		Segment<K,V>[] ss = (Segment<K,V>[])new Segment[ssize];
		UNSAFE.putOrderedObject(ss, SBASE, s0); // ordered write of segments[0]
		this.segments = ss;
	}
	
	//modCount is used for spanning segment to indicate whether the segment is modified, see size()
	Segment(float lf, int threshold, HashEntry<K,V>[] tab) {
        this.loadFactor = lf;
        this.threshold = threshold;//The table is rehashed when its size exceeds this threshold
        this.table = tab;//volatile HashEntry<K,V>[] table
    }
	
	static final class HashEntry<K,V> {
        final int hash;
        final K key;
        volatile V value;
        volatile HashEntry<K,V> next;
        ...
	}
	