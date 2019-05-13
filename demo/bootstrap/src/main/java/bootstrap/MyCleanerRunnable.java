package bootstrap;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/5/13
 * Time: 10:59
 * Desc:
 */
public class MyCleanerRunnable implements Runnable {

    private String name;

    private Runnable run;

    public MyCleanerRunnable(String name, Runnable run) {
        this.name = name;
        this.run = run;
    }

    @Override
    public void run() {
        System.out.println("my cleaner name: " + name);
        run.run();
    }
}
