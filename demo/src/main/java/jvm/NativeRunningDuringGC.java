package jvm;

// java -agentpath:/Users/liyang/libnativeRunningDuringGC.dylib jvm.NativeRunningDuringGC
// 不要用idea来跑

public class NativeRunningDuringGC {

    private Object obj = new BigObject();

    public static class GcTask implements Runnable {

        private final NativeRunningDuringGC task;

        public GcTask(NativeRunningDuringGC task) {
            this.task = task;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                task.obj = null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.gc();
            task.obj = new BigObject();
            task.obj = new BigObject();
            task.obj = new BigObject();
        }
    }

    public static void main(String[] args) {
        NativeRunningDuringGC task = new NativeRunningDuringGC();
        new Thread(new GcTask(task)).start();
        int ret = task.run(0);
        System.out.println("ret is " + ret);
    }

    public native int run(int value);
}
