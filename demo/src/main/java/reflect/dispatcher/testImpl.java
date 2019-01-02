package reflect.dispatcher;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/12/30
 * Time: 12:21
 * Desc:
 */
public class testImpl implements testInterface {

    @Override
    public void testMethod() {
        System.out.println("testMethod is called");
    }

    @Override
    public void testDispatcher(Object c) {
        System.out.println("testDispatcher Object is called");
    }

    @Override
    public void testDispatcher(Comparable c) {
        System.out.println("testDispatcher Comparable is called");
    }

    @Override
    public void testBoxDispatcher(int c) {
        System.out.println("testBoxDispatcher int is called");
    }

    @Override
    public void testBoxDispatcher(Integer c) {
        System.out.println("testBoxDispatcher Integer is called");
    }

    @Override
    public void testAutoBoxDispatcher(Integer c) {
        System.out.println("testAutoBoxDispatcher Integer is called");
    }
}
