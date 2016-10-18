package reflection;

import annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny.ly on 2016/2/2.
 */
public class GenericPractice<E> {
    private List<String> myParams;
    @Test
    public final <T> List<? extends String> getMyParams(Map<String, Object> params){return myParams;}
    public static void main(String args[]){
        Class<?> claz= GenericPractice.class;
        try {
            Method method = claz.getMethod("getMyParams",new Class[]{Map.class});
            System.out.println(method.isAnnotationPresent(Test.class));
            Type type=method.getGenericReturnType();
            if(type instanceof ParameterizedType){
                System.out.println("ParameterizedType: "+type);
                ParameterizedType t = (ParameterizedType)type;
                Type[] ts = t.getActualTypeArguments();
                if(ts[0] instanceof WildcardType){
                    System.out.println("WildcardType: "+ts[0].toString());
                }
                System.out.println("ParameterizedType end");
            }
            if(type instanceof WildcardType){
                System.out.println("WildcardType: "+type);
            }
            Annotation[] as = method.getDeclaredAnnotations();
            for(Annotation a:as){
                System.out.println(a.annotationType().getName());
            }
            TypeVariable<?>[] types=method.getTypeParameters();

            for(TypeVariable tv:types){
               System.out.println("TypeVariable: "+tv.getName());
               }

            System.out.println("Type: "+type.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
