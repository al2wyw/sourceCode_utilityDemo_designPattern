package reflect.dispatcher;

import sun.reflect.MethodAccessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
//package private access flag is 0x00, Modifier has no constant or method to check
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
/**
 *
public class GeneratedMethodAccessor1 extends MethodAccessorImpl {
    public GeneratedMethodAccessor1() {
        super();
    }

    public Object invoke(Object obj, Object[] args)
            throws IllegalArgumentException, InvocationTargetException {
        // prepare the target and parameters
        if (obj == null) throw new NullPointerException();
        try {
            A target = (A) obj; //type cast, if obj is an object of parent class, here will throw exception
            if (args.length != 1) throw new IllegalArgumentException();
            String arg0 = (String) args[0]; //type cast
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(e.toString());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e.toString());
        }
        // make the invocation
        try {
            target.foo(arg0);
        } catch (Throwable t) {
            throw new InvocationTargetException(t);
        }
    }
}
 */