package test;

/**
 * Created by apple on 1/07/2015.
 */
public class test_consturctor_sleep_thread {

    private int i;

    //the address value should be assigned after this constructor finish, does not know how to assign the address before constructor finish
    public test_consturctor_sleep_thread(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        i = 100;
        System.out.println("assign the i");
    }

    public int get(){
        return i;
    }

    public static void main(String args[]){
        TargetHolder t = new TargetHolder();
        new TestThread(t).start();
        t.setup();
    }
}

class TestThread extends Thread{

    private TargetHolder target;

    public TestThread(TargetHolder target){
        this.target = target;
    }

    @Override
    public void run() {
        while(target.get()==null);
        int i = target.get().get();
        System.out.println("get the result from "+i);
    }
}

class TargetHolder {
    //volatile for visible for the other thread
    private volatile test_consturctor_sleep_thread test;

    public void setup(){
        test = new test_consturctor_sleep_thread();
    }

    public test_consturctor_sleep_thread get(){
        return test;
    }
}