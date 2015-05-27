package com.test;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class test_lazy_autowired_jdk implements LazyInterceptor{

		public test_lazy_autowired_jdk(){
			System.out.println("test for lazy autowired jdk instantiation");
		}
		public void lazyAutowiredIntercept(){
			System.out.println("test for lazy autowired jdk Intercept");
			System.out.println("test for proxy with jrebel");
		}


}
