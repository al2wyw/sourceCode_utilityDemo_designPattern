package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by johnny.ly on 2016/6/11.
 */
public class testSecurityManager {
    public static void main(String[] args) throws Exception{
        if(System.getSecurityManager()!=null){
            System.out.println("manager");
        }
        /**
         * 使用 -Djava.security.manager 后必须在java.policy里面增加一条
         * permission java.lang.reflect.ReflectPermission "suppressAccessChecks";
         * */
        PrivateField test = new PrivateField();
        test.setI(10);
        Field i = test.getClass().getDeclaredField("i");
        if((!Modifier.isPublic(i.getModifiers()) || !Modifier.isPublic(i.getDeclaringClass().getModifiers())) && !i.isAccessible()){//由于有ReflectPermission("suppressAccessChecks")在,可能会出现SecurityException,所以最好判断是否有必要调用setAccessible
            i.setAccessible(true);//just set the override flag of AccessibleObject to true
            /**
             * A value of {@code true} indicates that
             * the reflected object should suppress Java language access checking
             * */
        }
        int res = (Integer)i.get(test);
        System.out.println(res);

        //singleton不在是single了
        Constructor<Singleton> factory = Singleton.class.getDeclaredConstructor(null);
        if((!Modifier.isPublic(i.getModifiers()) || !Modifier.isPublic(i.getDeclaringClass().getModifiers())) && !factory.isAccessible()){
            factory.setAccessible(true);
        }
        Singleton singleton = factory.newInstance(null);
        singleton.setI(100);
        System.out.println(singleton.getI());
    }
}

class PrivateField {
    private int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}

class Singleton{
    private Singleton(){

    }
    private int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
