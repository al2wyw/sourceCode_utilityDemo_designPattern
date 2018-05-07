package reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by apple on 18/04/2015.
 */
public class test_abstract_reflect {
    public static void main(String[] args) throws NoSuchMethodException {
        Class k = test.class;
        Method m = k.getMethod("use",new Class<?>[0]);
        if(m!=null){
            System.out.println("can get use method");
        }

        int ms = m.getModifiers();
        if(Modifier.isAbstract(ms)){
            System.out.println("abstract modifier");
        }
    }
}

abstract class test{
    abstract public void use();
    public void call(){

    }
}