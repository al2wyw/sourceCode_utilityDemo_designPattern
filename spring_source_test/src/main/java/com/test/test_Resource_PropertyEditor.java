package com.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletContextResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
/**
 *  classpath:  cp下的所有jar包和classes目录都会查找直到找到一个路径完全匹配resource
 *  classpath*: cp下的所有jar包和classes目录都会查找并返回多个路径完全匹配resource (路径也可以含有*)
 * */
@Component
public class test_Resource_PropertyEditor implements ApplicationContextAware{
	@Value("classpath:springmvc.xml")//ResourceEditorRegistrar
	private Resource temple;

	@Value("classpath*:META-INF/LICENSE")
	private Resource[] files;

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void call() {
		try{
			if(ServletContextResource.class.isInstance(temple)){
				ServletContextResource t = (ServletContextResource)temple;
				System.out.println(t.getFile().getAbsolutePath());
				System.out.println(t.getPathWithinContext());
				System.out.println(t.getPath());
			}
			InputStream in = temple.getInputStream();
			Reader r = new InputStreamReader(in);
			char[] cbuf=new char[1024];
			r.read(cbuf);
			System.out.println(cbuf);

			Resource res = applicationContext.getResource("classpath:META-INF/LICENSE"); // DefaultResourceLoader
			System.out.println(res);
			System.out.println(res.getURL());//classLoader.getResource("META-INF/LICENSE")
			System.out.println("resources========");
			Resource[] test = applicationContext.getResources("classpath*:META-INF/LICENSE");//PathMatchingResourcePatternResolver
			//最终调用 classLoader.getResources("META-INF/LICENSE")
			Arrays.stream(test).forEach(System.out::println);

		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
