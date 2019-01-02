package reflect.dispatcher;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/12/29
 * Time: 22:41
 * Desc:
 */
public interface testInterface {

    void testMethod();

    void testDispatcher(Object c);

    void testDispatcher(Comparable c);

    void testBoxDispatcher(int c);

    void testBoxDispatcher(Integer c);

    void testAutoBoxDispatcher(Integer c);
}
