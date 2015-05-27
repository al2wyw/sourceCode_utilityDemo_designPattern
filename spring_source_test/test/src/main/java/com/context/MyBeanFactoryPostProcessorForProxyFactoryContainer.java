package com.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessorForProxyFactoryContainer implements
		BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		ProxyFactoryContainer proxyFactoryContainer = ProxyFactoryContainer.getInstance();
		beanFactory.registerSingleton("proxyFactoryContainer", proxyFactoryContainer);
	}

}
