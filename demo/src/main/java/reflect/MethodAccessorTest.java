package reflect;

import sun.reflect.MethodAccessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodAccessorTest {

    public static void main(String args[]) throws Exception{
        Method target = Target.class.getMethod("execute", int.class);
        target.invoke(new Target(),10);


        setupWithReflectAccess(target);
        //setup(target);

        target.invoke(new Target(),10);
    }

    //class的反射不需要考虑class本身的access flag，member又可以override access check无视member本身和其对应的declaring class的access flag
    private static void setupWithReflectAccess(Method target) throws Exception{
        Class klass = Class.forName("java.lang.reflect.ReflectAccess");
        Method method = klass.getMethod("setMethodAccessor", Method.class, MethodAccessor.class);
        method.setAccessible(true);//ReflectAccess is package private, so override the ensureMemberAccess check
        //Object ra = klass.newInstance();//sun.reflect.Reflection.ensureMemberAccess() will throw exception
        Constructor constructor = klass.getDeclaredConstructor();
        constructor.setAccessible(true); //override the ensureMemberAccess check
        Object ra = constructor.newInstance();
        method.invoke(ra, target, new MethodInvoker());
    }

    private static void setup(Method target) throws Exception{
        Method setMethodAccessor = Method.class.getDeclaredMethod("setMethodAccessor", MethodAccessor.class);
        setMethodAccessor.setAccessible(true);
        setMethodAccessor.invoke(target,new MethodInvoker());
    }

    static class Target{
        public void execute(int i){
            i++;
            System.out.println("target invoked " + i);
        }
    }

    static class MethodInvoker implements MethodAccessor {
        @Override
        public Object invoke(Object obj, Object[] args) throws IllegalArgumentException, InvocationTargetException {
            System.out.println("invoke method: " + obj);
            return null;
        }
    }
}