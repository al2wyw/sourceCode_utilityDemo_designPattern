package reflect;

import java.lang.reflect.*;
import java.util.ArrayList;

/**
 *  ParameterizedType: java.util.ArrayList<java.lang.String> (带<>的)
 *  TypeVariable: E
 *  Class: java.lang.String
 *  WildcardType: ? extends java.lang.Number
 *  GenericArrayType: E[]
 *
 *  一个Type可以划分为Class和ParameterizedType， ParameterizedType又包含Class，TypeVariable，WildcardType 和 GenericArrayType
 *
 *  TypeParameters 只有在method和class才有(GenericDecalaration)： public static<E> E methodIV(） 的E
 *
 *  all @interface extends Annotation, Annotation.annotationType returns Class< ? extends Annotation >
 *  GenericArrayType represents an array type whose component type is either a parameterized type or a type variable.
 * */
public class test_type_reflect {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		// TODO Auto-generated method stub
		Class<?> klass = test_type_reflect.class;

		Method[] ms = klass.getDeclaredMethods();
		for(Method m:ms){
			System.out.println(m.getName() + " : " + m.getParameterCount() + " " + (m.getTypeParameters().length > 0 ? m.getTypeParameters()[0].getName() : null));
			for(Type type:m.getGenericParameterTypes()){
				System.out.println("the generic search start ");

				test(type);

				System.out.println("the generic search end");
			}
			for(Type type: m.getParameterTypes()){
				test(type);
			}
		}
	}
	
	public static void test(Type type){
		if(type instanceof ParameterizedType){
			System.out.println("ParameterizedType------"+type.getTypeName());
			Type[] ptype = ((ParameterizedType)type).getActualTypeArguments();
			for(Type atype:ptype){
				if(atype instanceof TypeVariable)
					System.out.println("TypeVariable------"+atype.getTypeName());
				else if(atype instanceof Class)
					System.out.println("Class------"+atype.getTypeName());
				else if(atype instanceof WildcardType)
					System.out.println("WildcardType------"+atype.getTypeName());
				else if(atype instanceof GenericArrayType)
					System.out.println("GenericArrayType------"+atype.getTypeName());
				else
					test(atype);
			}
		}else if(type instanceof Class){
			System.out.println("Class------"+type.getTypeName());
		}else{
			System.out.println("Type------"+type.getTypeName());
		}
	}
	
	public static<E> E methodIV(  
			ArrayList<ArrayList<E>> al1,  
			ArrayList<String> al2,  
			ArrayList<E> al3,  
			ArrayList<? extends Number> al4,  
			ArrayList<E[]> al5,
			String al6){
				return null;
			} 
}

