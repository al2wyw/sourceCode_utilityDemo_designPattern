java package visitor / protected visitor outside the package

some Pointcuts are themselves MethodMatchers, some are themselves ClassFilters too.

main classes: DefaultBeanFactoryPointcutAdvisor, AspectJPointcutAdvisor, AspectJExpressionPointcut, AbstractAspectJAdvice(sub classes), DefaultPointcutAdvisor(created by addAdvise of AdivsedSupport)

ClassFilter has match(Class<?> k)
MethodMatcher has match(Method method, Class<?> targetClass) and match(Method method, Class<?> targetClass, Object[] args) and match(Method method, Class<?> targetClass, boolean beanHasIntroductions)(introductionAwareMethodMatcher)
org.aopalliance.intercept.MethodInterceptor 's subclasses to check the implementation
(Pointcut's subclass, MethodMatcher's subclass, ClassFilter's subclass) to check the type
(Advisor's subclass) to check the type

self-invocation issue of proxy-based AOP use AspectJ to enable load time weaving to solve the problem.
proxy-based AOP can not intercept the protected/package method see AopUtils.canApply, but CGLIB itself can intercept the protected/package method see ClassUtils.getMostSpecificMethod??? and ClassUtils.isOverridable???

ProxyCallbackFilter.accept know which method is used, such like DynamicAdvisedInterceptor(General purpose AOP callback)

ThrowsAdviceInterceptor AspectJAfterThrowingAdvice the same???

ProxyConfig -> AbstractAutoProxyCreator(beanPostProcessor) -> AbstractAdvisorAutoProxyCreator(get advisors for target)
ProxyConfig -> AdvisedSupport(manipulate advisors) -> ProxyCreatorSupport(has AopProxyFactory to create AopProxy) -> ProxyFactory(get the proxy)

MethodInterceptor similar to  Advice

The default implementation considers Advices, Advisors and AopInfrastructureBeans as infrastructure classes. not attempt to auto-proxy infrastructure.

ProxyConfig is the super class for all aop creators and factorybeans and factories

to get the target object : JoinPoint or Advised; to get the proxy object: JoinPoint or AopContext;
ExposeInvocationInterceptor to get the current MethodInvocation for AbstractAspectJAdvice, that is why we can have ProceedingJoinPoint

JdkDynamicAopProxy has its own equal/hashCode function ???

commonInterceptors come from interceptorNames (setup via BeanNameAutoProxyCreator or AbstractAdvisorAutoProxyCreator)

opaque whether can be cast to Advised

invocableClone of MethodInvocation to use all the objects shared and an independent chain index

AspectJExpressionPointcut extended from introductionAwareMethodMatcher to match with Introductions

bind arguments how??? should read through AbstractAspectJAdvice !!!

use singletonTargetSource to create proxy, AopProxy wraps TargetSource, TargetSource wraps target bean.

ProceedingJoinPoint proceed is different form MethodInvocation proceed with invocableClone called
ProceedingJoinPoint has MethodInvocation, MethodInvocation has proxied method and interceptor chains
ProceedingJoinPoint is used to change arguments and change the returning value
ProceedingJoinPoint can be used for around advise only

new equals and hashCode for proxy object ???

dynamic method matcher match twice (the second time to match the real arguments)

AbstractAspectJAdvice has property method, my defined advise will be a property of AfterReturningAdviceInterceptor or MethodBeforeAdviceInterceptor or ThrowsAdviceInterceptor
AbstractAspectJAdvice has five subclass, AspectJAfterReturningAdvice and AspectJMethodBeforeAdvice do not implement MethodInterceptor will be the property of AfterReturningAdviceInterceptor or MethodBeforeAdviceInterceptor

aop:config -> ConfigBeanDefinitionParser -> AspectJAwareAdvisorAutoProxyCreator
aop:aspectj-autoproxy(proxy-target-class=false){include(name="")} -> AspectJAutoProxyBeanDefinitionParser -> AnnotationAwareAspectJAutoProxyCreator
aop:scoped-proxy(proxy-target-class=true) -> ScopedProxyBeanDefinitionDecorator add ScopedProxyFactoryBean to replace the original bean, it returns a proxy and the proxy has SimpleBeanTargetSource.getTarget to retrieve the origianl bean from beanfactory.
original bean name is changed to "scopedTarget.beanName" to hide the original bean
see ScopedProxyUtils.createScopedProxy and ScopedProxyFactoryBean.setBeanFactory

The following is written by the order of appearance in the source code(do not change the order):
-------------source code starts----------------

shouldSkip isInfrastructure

TargetSource targetSource = getCustomTargetSource(beanClass, beanName) if no custom target source(created by TargetSourceCreator in customTargetSourceCreators), will create proxy after initialization, but the conditions of wrapIfNeccessary is "have advice matched". getEarlyBeanReference will just only return wrapIfNeccessary.

(all called methods are in the AbstractAdvisorAutoProxyCreator, it has BeanFactoryAdvisorRetrievalHelper to retrieve all advisors)
getAdvicesAndAdvisorsForBean return advisors(Advisor) for bean from beanfactory{
//retrieve all the advisors
findCandidateAdvisors{
	return this.advisorRetrievalHelper.findAdvisorBeans();{
		advisorNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(this.beanFactory, Advisor.class, true, false);{
			String[] result = lbf.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);//see spring_source_note
			if (lbf instanceof HierarchicalBeanFactory) {
				if (hbf.getParentBeanFactory() instanceof ListableBeanFactory) {
					String[] parentResult = beanNamesForTypeIncludingAncestors(
					(ListableBeanFactory) hbf.getParentBeanFactory(), type, includeNonSingletons, allowEagerInit);
					for (String beanName : parentResult) {
						if (!resultList.contains(beanName) && !hbf.containsLocalBean(beanName)) {
							resultList.add(beanName);
						}
					}
					result = StringUtils.toStringArray(resultList);
				}
			}
			return result;
		}
		if (isEligibleBean(name)) {//different creators have different rules for advisor bean names
			for(name : advisorNames){
				advisors.add(this.beanFactory.getBean(name, Advisor.class));
			}
		}
		return advisors;
	}
}
AopUtils.findAdvisorsThatCanApply { 
	//how to find eligible advisors coming with introduction; 
	for (Advisor candidate : candidateAdvisors) {
		//canApply call canApply with hasIntroductions set to false
		if (candidate instanceof IntroductionAdvisor && canApply(candidate, clazz)) {
			eligibleAdvisors.add(candidate);
		}
	}
	boolean hasIntroductions = !eligibleAdvisors.isEmpty();
	for (Advisor candidate : candidateAdvisors) {
		if (candidate instanceof IntroductionAdvisor) {
			// already processed
			continue;
		}
		if (canApply(candidate, clazz, hasIntroductions)) {
			eligibleAdvisors.add(candidate);
		}
	}
	return eligibleAdvisors;
}
AspectJProxyUtils.makeAdvisorChainAspectJCapableIfNecessary -> foundAspectJAdvice = true -> advisors.add(0, ExposeInvocationInterceptor.ADVISOR);//DefaultPointcutAdvisor contains ExposeInvocationInterceptor used by AspectJExpressionPointcut and AbstractAspectJAdvice to get currentInvocation.
finally sort the advisors
}

ClassUtils.getAllInterfacesForClassAsSet return all the interfaces including the interface implemented by superclass, no interface extended by the interface

//always Advisor no need to adapt
AbstractAutoProxyCreator.buildAdvisors return Advisor or adapt(advisorAdapterRegistry) the MethodInterceptor/MethodBeforeAdvice/AfterReturningAdvice/ThrowsAdvice to DefaultPointcutAdvisor

proxyFactory has AopProxyFactory to createAopProxy and pass itself to AopProxy

JdkDynamicAopProxy.invoke{
			if (!this.equalsDefined && AopUtils.isEqualsMethod(method)) {
				// The target does not implement the equals(Object) method itself.
				return equals(args[0]);
			}
			if (!this.hashCodeDefined && AopUtils.isHashCodeMethod(method)) {
				// The target does not implement the hashCode() method itself.
				return hashCode();
			}
			if (!this.advised.opaque && method.getDeclaringClass().isInterface() &&
					method.getDeclaringClass().isAssignableFrom(Advised.class)) {
				// Service invocations on ProxyConfig with the proxy config...
				return AopUtils.invokeJoinpointUsingReflection(this.advised, method, args);
			}
			
			if (this.advised.exposeProxy) {
				// Make invocation available if necessary.
				oldProxy = AopContext.setCurrentProxy(proxy);
				setProxyContext = true;
			}
			
			// May be null. Get as late as possible to minimize the time we "own" the target,
			// in case it comes from a pool.
			target = targetSource.getTarget();
			if (target != null) {
				targetClass = target.getClass();
			}
			// Get the interception chain for this method.
			List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
			
			if (chain.isEmpty()) {
				retVal = AopUtils.invokeJoinpointUsingReflection(target, method, args);
			}
			else {
				invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass, chain);
				retVal = invocation.proceed(){
					if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
						return invokeJoinpoint();//call the intercepted method
					}
					
					Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
					//DynamicMethodMatcher isRuntime return true
					if (interceptorOrInterceptionAdvice instanceof InterceptorAndDynamicMethodMatcher) {
						// Evaluate dynamic method matcher here:
						InterceptorAndDynamicMethodMatcher dm =
								(InterceptorAndDynamicMethodMatcher) interceptorOrInterceptionAdvice;
						if (dm.methodMatcher.matches(this.method, this.targetClass, this.arguments)) {
							return dm.interceptor.invoke(this);
						}
						else {
							return proceed();
						}
					}
					else {
						// It's an interceptor, so we just invoke it: The pointcut will have
						// been evaluated statically before this object was constructed.
						return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
					}
				
				}
			}
			
			Class<?> returnType = method.getReturnType();
			if (retVal != null && retVal == target && returnType.isInstance(proxy) &&
					!RawTargetAccess.class.isAssignableFrom(method.getDeclaringClass())) {
				// Special case: it returned "this" and the return type of the method
				// is type-compatible. Note that we can't help if the target sets
				// a reference to itself in another returned object.
				retVal = proxy;
			} else if (retVal == null && returnType != Void.TYPE && returnType.isPrimitive()) {
				throw new AopInvocationException("Null return value from advice does not match primitive return type for: " + method);
			}
			
			finally :{
				if (target != null && !targetSource.isStatic()) {
					// Must have come from TargetSource.
					targetSource.releaseTarget(target);
				}
				if (setProxyContext) {
					// Restore old proxy.
					AopContext.setCurrentProxy(oldProxy);
				}
			}
}

methodInterceptor will be cached for MethodCacheKey(addAdvice will clear the cache)
this.advised.getInterceptorsAndDynamicInterceptionAdvice(DefaultAdvisorChainFactory) return methodInterceptor for MethodCacheKey{
		for (Advisor advisor : config.getAdvisors()) {
			if (advisor instanceof PointcutAdvisor) {
				// Add it conditionally.
				PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
				//isPreFiltered by getAdvicesAndAdvisorsForBean
				if (config.isPreFiltered() || pointcutAdvisor.getPointcut().getClassFilter().matches(targetClass)) {
					//why it returns array ??? one maps to many?
					MethodInterceptor[] interceptors = registry.getInterceptors(advisor);{
						List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>(3);
						Advice advice = advisor.getAdvice();
						if (advice instanceof MethodInterceptor) {
							interceptors.add((MethodInterceptor) advice);
						}
						for (AdvisorAdapter adapter : this.adapters) {
							if (adapter.supportsAdvice(advice)) {
								interceptors.add(adapter.getInterceptor(advisor));
							}
						}
						if (interceptors.isEmpty()) {
							throw new UnknownAdviceTypeException(advisor.getAdvice());
						}
						return interceptors.toArray(new MethodInterceptor[interceptors.size()]);
					}
					MethodMatcher mm = pointcutAdvisor.getPointcut().getMethodMatcher();
					if (MethodMatchers.matches(mm, method, targetClass, hasIntroductions)) {
						if (mm.isRuntime()) {
							// Creating a new object instance in the getInterceptors() method
							// isn't a problem as we normally cache created chains.
							for (MethodInterceptor interceptor : interceptors) {
								//InterceptorAndDynamicMethodMatcher just has interceptor and method matcher, nothing more
								interceptorList.add(new InterceptorAndDynamicMethodMatcher(interceptor, mm));
							}
						}
						else {
							interceptorList.addAll(Arrays.asList(interceptors));
						}
					}
				}
			}
			else if (advisor instanceof IntroductionAdvisor) {
				IntroductionAdvisor ia = (IntroductionAdvisor) advisor;
				if (config.isPreFiltered() || ia.getClassFilter().matches(targetClass)) {
					Interceptor[] interceptors = registry.getInterceptors(advisor);
					interceptorList.addAll(Arrays.asList(interceptors));
				}
			}
			else {
				Interceptor[] interceptors = registry.getInterceptors(advisor);
				interceptorList.addAll(Arrays.asList(interceptors));
			}
		}
}

-------------source code ends----------------

AopUtils.canApply(Advisor advisor, Class<?> targetClass, boolean hasIntroductions){
	if (advisor instanceof IntroductionAdvisor) {
		return ((IntroductionAdvisor) advisor).getClassFilter().matches(targetClass);
	}
	else if (advisor instanceof PointcutAdvisor) {
		PointcutAdvisor pca = (PointcutAdvisor) advisor;
		return canApply(pca.getPointcut(), targetClass, hasIntroductions);{
			if (!pc.getClassFilter().matches(targetClass)) {
				return false;
			}
			MethodMatcher methodMatcher = pc.getMethodMatcher();
			if (methodMatcher instanceof IntroductionAwareMethodMatcher) {
				introductionAwareMethodMatcher = (IntroductionAwareMethodMatcher) methodMatcher;
			}
			Set<Class<?>> classes = new HashSet<Class<?>>(ClassUtils.getAllInterfacesForClassAsSet(targetClass));
			classes.add(targetClass);
			for (Class<?> clazz : classes) {
				Method[] methods = clazz.getMethods();//just return public methods !!!
				for (Method method : methods) {
					if ((introductionAwareMethodMatcher != null &&
							introductionAwareMethodMatcher.matches(method, targetClass, hasIntroductions)) ||
							methodMatcher.matches(method, targetClass)) {
						return true;
					}
				}
			}
			return false;
		}
	}
	else {
		// It doesn't have a pointcut so we assume it applies.
		return true;
	}
}
