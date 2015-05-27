package com.reflect.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
		Annotation[] a = klass.getAnnotationsByType(Component.class);
		System.out.println(a.length);
		
		System.out.println(Component.class.getSimpleName());
		Annotation an = klass.getAnnotation(Component.class);
		Class<?> antype = an.annotationType();
		if(antype.equals(Component.class)){
			System.out.println("good");
		}
		if(an.getClass().equals(Component.class)){
			System.out.println("bad");
		}
		//do not know how to get the annotations on annotation interface
	}
	//AnnotatedType since 1.8
}

@Component
class Anno{
	@Autowired
	private Object test;
	
	@Bean
	public Object get(){
		return null;
	}
}