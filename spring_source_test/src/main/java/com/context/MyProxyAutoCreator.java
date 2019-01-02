package com.context;

import org.springframework.aop.Advisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.ClassUtils;

public class MyProxyAutoCreator extends AspectJAwareAdvisorAutoProxyCreator {

	private ProxyFactoryContainer container = ProxyFactoryContainer.getInstance();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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

		if(!proxyFactory.isExposeProxy()){
			proxyFactory.setExposeProxy(true);
		}

		if(container!=null)
			container.addProxyFactory(beanName, proxyFactory);
			
		return proxyFactory.getProxy(ClassUtils.getDefaultClassLoader());
	}
	
}
