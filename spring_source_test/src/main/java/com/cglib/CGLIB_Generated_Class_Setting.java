package com.cglib;

import java.io.File;
import net.sf.cglib.core.DebuggingClassWriter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class CGLIB_Generated_Class_Setting implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("setting up the system property");

		File f = new File(beanFactory.getBeanClassLoader().getResource("/").getFile());
		String path = f.getPath();
		System.out.println(path);
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,path);
	}
}
