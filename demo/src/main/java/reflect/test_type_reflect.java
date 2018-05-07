package reflect;

import java.lang.reflect.*;
import java.util.ArrayList;

public class test_type_reflect {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		// TODO Auto-generated method stub
		Class<?> klass = test_type_reflect.class;
		
		Method[] ms = klass.getDeclaredMethods();
		for(Method m:ms){
			System.out.println(m.getName()+ " : " +m.getParameterCount());
			for(Type type:m.getGenericParameterTypes()){
				System.out.println("start the generic search");

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
					System.out.println("TypeVariable------"+type.getTypeName());
				if(atype instanceof Class)
					System.out.println("Class------"+type.getTypeName());
				if(atype instanceof WildcardType)
					System.out.println("WildcardType------"+type.getTypeName());
				if(atype instanceof GenericArrayType)
					System.out.println("GenericArrayType------"+type.getTypeName());

				test(atype);

			}
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

