package demo;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by johnny.ly on 2016/4/6.
 */
public class testSetAddAll {
    public static void main(String[] args){
        List<String> test  = Lists.asList("test1",new String[]{"test2","test4","test1"});
        System.out.println(Joiner.on(",").join(test));
        Set<String> test1 = new LinkedHashSet<String>();
        test1.addAll(test);
        System.out.println(Joiner.on(",").join(test1));
    }
}
