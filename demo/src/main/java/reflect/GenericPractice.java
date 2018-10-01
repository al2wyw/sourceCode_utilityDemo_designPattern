package reflect;

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

    private E obj;

    @Test
    public final <T> List<? extends String> getMyParams(Map<String, T> params){return myParams;}

    public static void main(String args[]){
        Class<?> claz= GenericPractice.class;
        try {
            Method method = claz.getMethod("getMyParams",new Class[]{Map.class});
            Annotation[] annotations = Test.class.getAnnotations();
            for(Annotation an : annotations){
                System.out.println(an.annotationType());
            }
            System.out.println(method.isAnnotationPresent(Test.class));
            Type type=method.getGenericReturnType();
            //ParameterizedType -> List<? extends String> 所有的容器类和带参数的类型
            if(type instanceof ParameterizedType){
                System.out.println("ParameterizedType: "+type);
                ParameterizedType t = (ParameterizedType)type;
                Type[] ts = t.getActualTypeArguments(); //仅仅返回第一层<>里面的Type
                if(ts[0] instanceof WildcardType){
                    System.out.println("WildcardType: "+ts[0].toString());
                }
                System.out.println("ParameterizedType end");
            }

            //WildcardType -> ? extends String
            if(type instanceof WildcardType){
                System.out.println("WildcardType: "+type);
            }
            Annotation[] as = method.getDeclaredAnnotations();
            for(Annotation a:as){
                System.out.println(a.annotationType().getName());
            }

            //TypeVariable -> T
            TypeVariable<?>[] types=method.getTypeParameters();
            for(TypeVariable tv:types){
               System.out.println("TypeVariable: "+tv.getName());
               }

            System.out.println("Type: " + type.toString());

            Field obj = claz.getDeclaredField("obj");
            Class objKlass = obj.getType();
            System.out.println("obj type: " + objKlass.getName());
            Type objType = obj.getGenericType();
            System.out.println("obj generic type: " + objType.getTypeName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
