package beanutils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/12/14
 * Time: 11:47
 * Desc:
 */
public class testBeanToMap {
    public static void main(String[] args) throws Exception{
        Test test = new Test();
        List<Integer> ints = Lists.newArrayList(1,2,3);
        Map<String,Object> objects = Maps.newHashMap();
        objects.put("test1", "test");
        objects.put("test2", new Test("test2"));
        test.setInts(ints);
        test.setObjects(objects);
        test.setName("my test");
        Map<String,Object> map =  transformToMap(test);
        System.out.print("test");
    }

    public static <T> Map<String,Object> transformToMap(T target){
        if(target == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(target);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            //log.error("transform To Map exception",e);
        }

        return map;
    }
}

class Test{
    private List<Integer> ints;
    private Map<String,Object> objects;
    private String name;

    public Test(String name) {
        this.name = name;
    }

    public Test(){}

    public List<Integer> getInts() {
        return ints;
    }

    public void setInts(List<Integer> ints) {
        this.ints = ints;
    }

    public Map<String, Object> getObjects() {
        return objects;
    }

    public void setObjects(Map<String, Object> objects) {
        this.objects = objects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
