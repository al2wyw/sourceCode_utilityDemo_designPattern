package beanutils;

import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/12/18
 * Time: 21:26
 * Desc:
 */
public class TestBeanIntrospect {

    public static void main(String[] args) throws Exception{
        Son son = new Son();
        son.setName("peter");
        son.setI(10);
        son.setSex("male");
        Father father = new Father();
        copyValue(father,son);
        father = new Father();
        BeanUtils.copyProperties(father,son);//default Introspector.getBeanInfo(clazz, clazz.getSuperClass());
        System.out.print("");

    }

    public static void copyValue(Object toObject, Object fromObject) {
        if (fromObject == null || toObject == null) {
            return;
        }
        try{
            List<PropertyInfo> fromPropertyInfos = getBeanProperties(fromObject.getClass());
            List<PropertyInfo> toPropertyInfos = getBeanProperties(toObject.getClass());
            for (PropertyInfo toPropertyInfo : toPropertyInfos) {
                PropertyInfo fromPropertyInfo = null;
                for(PropertyInfo propertyInfo : fromPropertyInfos){
                    if(propertyInfo.name.equalsIgnoreCase(toPropertyInfo.name)){
                        fromPropertyInfo = propertyInfo;
                        break;
                    }
                }
                if(fromPropertyInfo == null){
                    continue;
                }
                Object value = fromPropertyInfo.readMethod.invoke(fromObject);
                if (value != null) {
                    try{
                        toPropertyInfo.writeMethod.invoke(toObject, value);
                    }catch (Exception e){
                        //log.error("copyValue error,property : " + toPropertyInfo.name + ";error:" + e.getMessage());
                    }
                }
            }
        }catch (Exception e) {
           // log.warn("copyValue error", e);
        }
    }

    private static <T> List<PropertyInfo> getBeanProperties(Class<T> clazz) throws IntrospectionException {
        List<PropertyInfo> propertyInfos = null;

        if (propertyInfos == null) {
            // the stop class is very important, clazz.getSuperClass is not correct
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
            PropertyDescriptor pds[] = beanInfo.getPropertyDescriptors();
            propertyInfos = new ArrayList<PropertyInfo>(pds.length);
            for (PropertyDescriptor pd : pds) {
                if (pd.getWriteMethod() != null && pd.getReadMethod() != null) {
                    propertyInfos.add(new PropertyInfo(pd.getName(), pd.getWriteMethod(), pd.getReadMethod()));
                }
            }

            ((ArrayList<PropertyInfo>) propertyInfos).trimToSize();

        }

        return propertyInfos;
    }

    public static class PropertyInfo {

        public String name;

        public Method writeMethod;

        public Method readMethod;

        public PropertyInfo(String name, Method writeMethod, Method readMethod) {
            this.name = name;
            this.writeMethod = writeMethod;
            this.readMethod = readMethod;

        }
    }

}

class Father{
    private String name;
    private int i;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}

class Son extends Father{
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
