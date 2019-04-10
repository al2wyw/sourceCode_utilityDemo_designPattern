package thread;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/4/10
 * Time: 12:32
 * Desc:
 *       i < 0 || i >= n || i + n >= nextn maybe a bug, i >= n || i + n >= nextn can not achieve
 *       sc == rs + 1 || sc == rs + MAX_RESIZERS is a bug, rs should be resizeStamp(n) << RESIZE_STAMP_SHIFT
 */
public class ConHashMapTest {
    public static void main(String[] args) throws Exception{
        ConcurrentHashMap<String ,Integer> values = new ConcurrentHashMap<>(4);
        for(int i = 0; i< 1000; i++) {
            values.put(String.valueOf(i), i);
        }
    }
}
