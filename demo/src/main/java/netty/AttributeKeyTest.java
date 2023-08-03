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

        test();
    }

    public static void test() {
        //DefaultAttributeMap 不会扩容，BUCKET_SIZE为4的情况下index冲突会很容易退化为list
        DefaultAttributeMap map0 = new DefaultAttributeMap();
        DefaultAttributeMap map1 = new DefaultAttributeMap();
        DefaultAttributeMap map2 = new DefaultAttributeMap();
        DefaultAttributeMap map3 = new DefaultAttributeMap();
        AttributeKey<String> key = null;
        for (int i = 0; i < 40; i++) {
            key = AttributeKey.valueOf("test" + i);
            switch (i % 4) {
                case 0: map0.attr(key).set("test" + i);break;
                case 1: map1.attr(key).set("test" + i);break;
                case 2: map2.attr(key).set("test" + i);break;
                case 3: map3.attr(key).set("test" + i);break;
            }
        }

        System.out.println(map3.attr(key).get());
    }
}
