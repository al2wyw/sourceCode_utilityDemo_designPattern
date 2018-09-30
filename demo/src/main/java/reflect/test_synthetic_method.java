package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/9/30
 * Time: 11:27
 * Desc:    内部类编译时会变成一个顶层类，而外部类又可以直接访问内部的私有字段，方法或者构造函数
 *          对于内部类任何private的字段，方法或者构造函数，如果它们被其它顶层类所使用，就会生成相应的synthetic方法供其他顶层类调用(没有使用到就不生成)
 00001008 SYNTHETIC|STATIC
 00000002 PRIVATE
 00000111 NATIVE|FINAL|PUBLIC
 00000011 FINAL|PUBLIC
 00000001 PUBLIC
 00001000 SYNTHETIC
 00000040 BRIDGE
 */
public class test_synthetic_method {

    public static class A {
        private A(){}
        private int x;
        private void x(){}
    }

    public static void main(String[] args) {
        A a = new A(); //synthetic construct
        a.x = 2; //synthetic setter & getter
        a.x(); //synthetic x()
        System.out.println(a.x);
        for (Method m : A.class.getDeclaredMethods()) {
            System.out.println(String.format("%08X", m.getModifiers()) + " " + m.getName());
        }
        System.out.println("--------------------------");
        for (Method m : A.class.getMethods()) {
            System.out.println(String.format("%08X", m.getModifiers()) + " " + m.getReturnType().getSimpleName() + " " + m.getName());
        }
        System.out.println("--------------------------");
        for( Constructor<?> c : A.class.getDeclaredConstructors() ){
            System.out.println(String.format("%08X", c.getModifiers()) + " " + c.getName());
        }
    }
}
