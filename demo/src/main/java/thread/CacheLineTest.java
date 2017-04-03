package thread;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/4/1
 * Time: 15:41
 * Desc: java object memory size???
 */
public class CacheLineTest implements Runnable {
    public final static long COUNT = 100L * 1000L * 1000L;
    public final static int THREADS = 4;
    private final int index;

    private static CacheLineSize[] cacheLines = new CacheLineSize[THREADS];

    static {
        for (int i = 0; i < THREADS; i++) {
            cacheLines[i] = new CacheLineSize();
        }
    }

    public CacheLineTest(final int index) {
        this.index = index;
    }

    public static void main(final String[] args) throws Exception {
        final long start = System.nanoTime();
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(new CacheLineTest(i));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        System.out.println(System.nanoTime() - start);
    }

    public void run() {
        long i = COUNT + 1;
        while (0 != --i) {
            cacheLines[index].p6 = i;
        }
    }

    public final static class CacheLineSize {
        //header 12 bytes
        public int p0;//4
        public long p1, p2, p3, p4, p5; //40 //commit out this line, it will be slow
        public volatile long p6 = 0L;//8
        //total:12+4+40+8=64
    }
}
