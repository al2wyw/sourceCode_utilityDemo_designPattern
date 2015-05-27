package com.context;

import org.springframework.aop.Advisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.ClassUtils;

public class MyProxyAutoCreator extends AspectJAwareAdvisorAutoProxyCreator implements BeanFactoryAware{

	private ProxyFactoryContainer container;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void setBeanFactory(BeanFactory f){
		container = (ProxyFactoryContainer)f.getBean("proxyFactoryContainer");
		if(container!=null)
			System.out.println("get the ProxyFactoryContainer");
	}
	
	protected Object createProxy(
			Class<?> beanClass, String beanName, Object[] specificInterceptors, TargetSource targetSource) {

		ProxyFactory proxyFactory = new ProxyFactory();
		// Copy our properties (proxyTargetClass etc) inherited from ProxyConfig.
		proxyFactory.copyFrom(this);

		if (!shouldProxyTargetClass(beanClass, beanName)) {
			// Must allow for introductions; can't just set interfaces to
			// the target's interfaces only.
			Class<?>[] targetInterfaces = ClassUtils.getAllInterfacesForClass(beanClass, ClassUtils.getDefaultClassLoader());
			for (Class<?> targetInterface : targetInterfaces) {
				proxyFactory.addInterface(targetInterface);
			}
		}

		Advisor[] advisors = buildAdvisors(beanName, specificInterceptors);
		for (Advisor advisor : advisors) {
			proxyFactory.addAdvisor(advisor);
		}

		proxyFactory.setTargetSource(targetSource);
		customizeProxyFactory(proxyFactory);

		proxyFactory.setFrozen(false);
		if (advisorsPreFiltered()) {
			proxyFactory.setPreFiltered(true);
		}

		if(container!=null)
			container.addProxyFactory(beanName, proxyFactory);
			
		return proxyFactory.getProxy(ClassUtils.getDefaultClassLoader());
	}
	
}
