package guava;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/11/29
 * Time: 16:41
 * Desc:
 */
public class testPrecondition {
    public static void main(String[] args) throws Exception{
        String test = null;
        Preconditions.checkArgument(test == null, "test");
        Map<Integer,Integer> map = Maps.newHashMap();
        map.put(1,1);
        map.put(2,2);
        map = Maps.filterEntries(map, new Predicate<Map.Entry<Integer, Integer>>() {
            public boolean apply(Map.Entry<Integer, Integer> input) {
                return input.getValue() == 1;
            }
        });
        System.out.println(Joiner.on(",").join(map.keySet()));
    }
}
