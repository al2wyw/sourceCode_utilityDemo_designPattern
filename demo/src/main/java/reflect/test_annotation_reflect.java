package reflect;


import annotation.Test;

import java.lang.annotation.Annotation;

public class test_annotation_reflect {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Class<Anno> klass = Anno.class;

		for(Annotation an : klass.getAnnotations()){
			System.out.println(an.annotationType().getName()+"  "+an.getClass().getName());
			for(Annotation ano :an.annotationType().getAnnotations()){

				System.out.println(ano.annotationType().getName());
			}
		}
		System.out.println();
		Annotation[] a = klass.getAnnotationsByType(Test.class);
		System.out.println(a.length);
		
		System.out.println(Test.class.getSimpleName());
		Annotation an = klass.getAnnotation(Test.class);
		Class<?> antype = an.annotationType();
		if(antype.equals(Test.class)){
			System.out.println("good");
		}
		if(an.getClass().equals(Test.class)){
			System.out.println("bad");
		}
		an = klass.getAnnotation(Test.class);
		System.out.println("Annotation: " + an.annotationType());
		an = klass.getDeclaredAnnotation(Test.class);
		System.out.println("DeclaredAnnotation: " + an.annotationType());

		if(klass.isAnnotationPresent(Test.class)){
			System.out.println("Test is present");
		}
		Class<Bnno> klassb = Bnno.class;
		if(klassb.isAnnotationPresent(Test.class)){//Test must have @Inherited
			System.out.println("Test is present");
		}
		an = klassb.getAnnotation(Test.class);
		System.out.println("Annotation: " + an.annotationType());
		//an = klassb.getDeclaredAnnotation(Test.class);
		//System.out.println("DeclaredAnnotation: " + an.annotationType());//npe
	}
	//AnnotatedType since 1.8
}

@Test
class Anno{
	private Object test;
	

	public Object get(){
		return null;
	}
}

class Bnno extends Anno{

}