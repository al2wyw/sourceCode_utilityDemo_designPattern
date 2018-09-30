package reflect;

import demoObject.UserInfo;
import demoObject.UserInfoExt;
import org.apache.commons.beanutils.BeanUtils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;

/**
 * Created by johnny.ly on 2016/1/12.
 */
public class beanUtilsDemo {
    public static void main(String[] args){
        test1();
        test2();
    }

    private static void test1(){
        UserInfo userInfo=new UserInfo();
        userInfo.setBirthday(Calendar.getInstance().getTime());
        try {
            BeanUtils.setProperty(userInfo, "birthday.time", "111111");
            Object obj = BeanUtils.getProperty(userInfo, "birthday.time");
            System.out.println(obj);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void test2(){
        UserInfo userInfo=new UserInfo();
        userInfo.setAge(11);
        userInfo.setUserId(54323434L);
        userInfo.setUserName("Ptere");
        UserInfoExt ext = new UserInfoExt();
        try {
            BeanUtils.copyProperties(ext, userInfo);
            System.out.println(ext.getUserName());
            try {
                PropertyDescriptor[] props = Introspector.getBeanInfo(ext.getClass()).getPropertyDescriptors();// getTargetPropertyInfo the last else show how to get properties
                Method m = ext.getClass().getMethod("test",null);

            }catch (Exception e){

            }
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

