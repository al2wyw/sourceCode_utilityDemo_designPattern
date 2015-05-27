package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletContextResource;

@Component
public class test_Resource_PropertyEditor {
	@Value("classpath:springmvc.xml")//ResourceEditorRegistrar
	private Resource temple;
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
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
