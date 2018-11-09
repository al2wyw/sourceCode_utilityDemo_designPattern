package reflect;

import annotation.TypeUseAnno;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/11/8
 * Time: 22:00
 * Desc: AnnotatedType includes ParameterizedType and annotation
 *       GenericType includes ParameterizedType
 */
public class AnnotatedTypeTest {
    public static void main(String[] args) throws Exception {
        Class klassb = Bnno.class;
        Type generic = klassb.getGenericSuperclass();
        System.out.println(generic.getTypeName());
        AnnotatedType annotatedType = klassb.getAnnotatedSuperclass();
        System.out.println(annotatedType.getAnnotation(TypeUseAnno.class));
    }

    class Anno<T>{
        private T test;

        public T get(){
            return null;
        }
    }

    class Bnno extends @TypeUseAnno(name = "test") Anno<Object>{
        @Override
        public Object get() {
            return super.get();
        }
    }
}
