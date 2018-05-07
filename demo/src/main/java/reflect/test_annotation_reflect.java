package reflect;


import annotation.Test;

import java.lang.annotation.Annotation;

public class test_annotation_reflect {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Class<Anno> klass = Anno.class;

		for(Annotation an : klass.getAnnotations()){
			System.out.println(an.annotationType().getSimpleName()+"  "+an.getClass().getSimpleName());
			for(Annotation ano :an.annotationType().getAnnotations()){

				System.out.println(ano.annotationType().getName());
			}
		}
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
		//do not know how to get the annotations on annotation interface
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