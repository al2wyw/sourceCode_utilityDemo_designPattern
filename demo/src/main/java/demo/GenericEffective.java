package demo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/9/1
 * Time: 19:45
 * Desc:
 */
public class GenericEffective {

    private interface Fun<T>{
        T apply(T a);
    }

    private static Fun<Object> fun = (a) -> a;

    @SuppressWarnings("unchecked")
    public <T> Fun<T> getFun(){
        return (Fun<T>) fun;
    }

    public static void main(String[] args)throws Exception{
        TestArg r = new TestArg();
        r.store(String.class, "test");
        r.store(Long.class, 1L);
        System.out.println(r.ret(String.class));
        System.out.println(r.ret(Long.class));
    }

    private static class TestArg {
        private Map<Class<?>, Object> map = new HashMap<>();

        public <E> E ret(Class<E> klass){
            return klass.cast(map.get(klass));
        }

        public <E> void store(Class<E> klass, E e){
            map.put(klass, e);
        }
    }
}