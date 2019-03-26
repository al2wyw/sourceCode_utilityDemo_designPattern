package netty;

import io.netty.util.AttributeKey;
import io.netty.util.DefaultAttributeMap;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/3/26
 * Time: 14:29
 * Desc:
 */
public class AttributeKeyTest {

    private static final AttributeKey<String> KEY = AttributeKey.valueOf("test");

    public static void main(String args[]) throws Exception{
        DefaultAttributeMap map = new DefaultAttributeMap();
        map.attr(KEY).set("test1");
        map.attr(KEY).set("test2");
        String value = map.attr(KEY).get();

        System.out.println(value);
    }
}
