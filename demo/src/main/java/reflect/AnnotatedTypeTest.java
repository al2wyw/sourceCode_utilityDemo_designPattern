package reflect;

import annotation.TypeUseAnno;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Stream;

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

        Method method = AnnotatedTypeTest.class.getMethod("test",List.class,String.class);
        AnnotatedType[] annotatedTypes = method.getAnnotatedParameterTypes();
        Stream.of(annotatedTypes).forEach(ant -> {
            System.out.println("AnnotatedType  " + ant.getType() + " " + ant.isAnnotationPresent(TypeUseAnno.class));
            if(ant instanceof AnnotatedParameterizedType){
                AnnotatedParameterizedType anpt = (AnnotatedParameterizedType) ant;
                Stream.of(anpt.getAnnotatedActualTypeArguments()).forEach(anptt -> System.out.println("AnnotatedParameterizedType  " + anptt.getType() + " " + anptt.isAnnotationPresent(TypeUseAnno.class)));
            }
        });
    }
    //TypeUseAnno must applied to TypeVariable, ArrayType, WildCardType and ParameterizeType, applied to Class does not count
    public void test(List<@TypeUseAnno(name="use1")String> list, @TypeUseAnno(name="use2") String tag){

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
