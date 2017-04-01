package reflection;

import com.google.common.collect.Lists;

import java.lang.reflect.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/4/1
 * Time: 12:09
 * Desc:
 */
public class TypeDemo <E>{

    public ArrayList<ArrayList<String>> al1;
    public ArrayList<E> al2;
    public ArrayList<String> al3;
    public ArrayList<? extends Number> al4;
    public ArrayList<E[]> al5;

    public static void main(String args[]) throws Exception {
        ArrayList<? extends Number> a = Lists.newArrayList();
        Type[] ts = a.getClass().getGenericInterfaces();
        Type type = ts[0];
        if (type instanceof ParameterizedType) {
            System.out.println(type.getTypeName() + " "+ ((ParameterizedType) type).getActualTypeArguments()[0].getTypeName());//java.util.List<E>  E
        }
        getActualType("al1");
        getActualType("al2");
        getActualType("al3");
        getActualType("al4");
        getActualType("al5");
        /**
         ParameterizedType: java.util.ArrayList<java.lang.String>
         TypeVariable: E
         Class: java.lang.String
         WildcardType: ? extends java.lang.Number
         GenericArrayType: E[]
         * */
    }

    public static void getActualType(String name) throws Exception{
        Class claz = TypeDemo.class;
        Type type = claz.getField(name).getGenericType(); //field,method,class 都有getGenericType方法
        if (type instanceof ParameterizedType) {
            Type[] ts = ((ParameterizedType) type).getActualTypeArguments();
            Type target = ts[0];
            if(target instanceof Class) {
                System.out.println("Class: " + target.getTypeName());
            }else if(target instanceof WildcardType){
                System.out.println("WildcardType: " + target.getTypeName());
            }else if(target instanceof TypeVariable){
                System.out.println("TypeVariable: " + target.getTypeName());
            }else if(target instanceof GenericArrayType){
                System.out.println("GenericArrayType: " + target.getTypeName());
            }else if(target instanceof ParameterizedType){
                System.out.println("ParameterizedType: " + target.getTypeName());
            }
        }
    }
}
/**
 *  all @interface extends Annotation, Annotation.annotationType returns Class< ? extends Annotation >
 *  GenericArrayType represents an array type whose component type is either a parameterized type or a type variable.
 * */