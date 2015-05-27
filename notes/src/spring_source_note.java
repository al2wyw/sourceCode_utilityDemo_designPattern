beanPostProcessors list is inside AbstractBeanFactory while beanFactoryPostProcessors list is inside AbstractApplicationContext
DefaultListableBeanFactory beanFactory is inside AbstractRefreshableApplicationContext
	
beanFactory.ignoreDependencyInterface(ApplicationContextAware.class)??? see ApplicationContextAwareProcessor
beanFactory.registerResolvableDependency(ApplicationContext.class, beanFactory)???

InstantiationStrategy(default ctor,ctor,factory method) -> SimpleInstantiationStrategy -> CglibSubclassingInstantiationStrategy

FactoryBean is used in BeanFactory(getObjectForBeanInstance) while ObjectFactory is a normal object generator

Bug ? Indexer will change Properties to Map and get value from a Map. see getValue of MapIndexingValueRef in Indexer.
However, Properties has a default Properties which created by Properties(Properties default), when Properties can not get 
the value, it will query from default Properties. Map will not do this!

how it works???
parserContext.pushContainingComponent(compositeDef)
parserContext.popAndRegisterContainingComponent()

CglibSubclassingInstantiationStrategy is used by method-overrides to instatiate bean

getLazyResolutionProxyIfNecessary what does the xml do with this ??? (just instantiate the bean later, and then let it pass through all the beanPostProcessors)

@Lazy with @Autowired will create lazyProxy, but the target should be tagged with @Lazy to instantiate later. @Lazy with aop getTarget (jdk/cglib) 

getBean has three args: beanname, args, classtype. always use beanname or beanname plus classtype  !!!
before getBean it will have condition judgement 

autowire-candidate="false" works for @Autowired/autowireByType not for autowireByname (a bug found???)

CandidateComponent
AutowireCandidate

addSingleton(String beanName, Object singletonObject) {
		synchronized (this.singletonObjects) {
			this.singletonObjects.put(beanName, (singletonObject != null ? singletonObject : NULL_OBJECT));
			this.singletonFactories.remove(beanName);
			this.earlySingletonObjects.remove(beanName);//!!!
			this.registeredSingletons.add(beanName);
		}
	}
addSingletonFactory(String beanName, ObjectFactory singletonFactory) {
		Assert.notNull(singletonFactory, "Singleton factory must not be null");
		synchronized (this.singletonObjects) {
			if (!this.singletonObjects.containsKey(beanName)) {
				this.singletonFactories.put(beanName, singletonFactory);
				this.earlySingletonObjects.remove(beanName);//!!!
				this.registeredSingletons.add(beanName);
			}
		}
	}
	
spring startup:

org.springframework.web.context.ContextLoaderListener extends ContextLoader implements ServletContextListener

ServletContextListener has:
	public void contextInitialized ( ServletContextEvent sce ){
		initWebApplicationContext(ContextLoader){
			createWebApplicationContext(ContextLoader){
				determineContextClass(ContextLoader){
					determine the application context, the default will be XmlWebApplicationContext
				}
			}
			setParent:loadParentContext(ContextLoader){
				servletContext.getInitParameter(LOCATOR_FACTORY_KEY_PARAM)
				if LOCATOR_FACTORY_KEY_PARAM is not set by web.xml, no parent!
			}
			configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext wac, ServletContext sc)(ContextLoader){
				setId: getInitParameter(CONTEXT_ID_PARAM)
				setServletContext: sc
				setConfigLocation:getInitParameter(CONFIG_LOCATION_PARAM)
				 (ContextLoader){
					determineContextInitializerClasses(ContextLoader){ (used for extension)
						getInitParameter(CONTEXT_INITIALIZER_CLASSES_PARAM)
						ApplicationContextInitializer class name is spilt by ","
					}
					ApplicationContextInitializer<C extends ConfigurableApplicationContext> has: void initialize(C applicationContext) will be called
				}
				refresh(AbstractApplicationContext){
					// Prepare this context for refreshing.
					prepareRefresh()(AbstractApplicationContext)

					// Tell the subclass to refresh the internal bean factory.
					ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory()(AbstractApplicationContext){
						refreshBeanFactory()(AbstractRefreshableApplicationContext){
							destroyBeans()(AbstractRefreshableApplicationContext)
							closeBeanFactory()(AbstractRefreshableApplicationContext)
							DefaultListableBeanFactory beanFactory = createBeanFactory()(AbstractRefreshableApplicationContext)
							beanFactory.setSerializationId(getId())(DefaultListableBeanFactory)
							customizeBeanFactory(beanFactory)(AbstractRefreshableApplicationContext) (used for extension)
							loadBeanDefinitions(beanFactory)(XmlWebApplicationContext){
								loadBeanDefinitions(AbstractBeanDefinitionReader){
								
									XmlBeanDefinitionReader,GroovyBeanDefinitionReader,PropertiesBeanDefinitionReader
									
									doLoadBeanDefinitions(XmlBeanDefinitionReader){
										registerBeanDefinitions(XmlBeanDefinitionReader){
										
											DefaultBeanDefinitionDocumentReader and BeanDefinitionParserDelegate are two important classes for parsing bean definitions
											
											registerBeanDefinitions(DefaultBeanDefinitionDocumentReader){
												doRegisterBeanDefinitions(DefaultBeanDefinitionDocumentReader){
												
													preProcessXml (used for extension)
													
													parseBeanDefinitions(DefaultBeanDefinitionDocumentReader){
														//the properties of ParserContext are important
														parseDefaultElement(DefaultBeanDefinitionDocumentReader);
														parseCustomElement(BeanDefinitionParserDelegate);
													}
												
													postProcessXml (used for extension)
												}
											}
										}
									}
								}
							}
						}
						ConfigurableListableBeanFactory beanFactory = getBeanFactory()(AbstractRefreshableApplicationContext)
						return beanFactory
					}

					// Prepare the bean factory for use in this context.
					prepareBeanFactory(beanFactory)(AbstractApplicationContext){
						beanFactory.setBeanClassLoader(getClassLoader());
						beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver());
						beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, getEnvironment()));

						// Configure the bean factory with context callbacks.
						beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
						beanFactory.ignoreDependencyInterface(ResourceLoaderAware.class);
						beanFactory.ignoreDependencyInterface(ApplicationEventPublisherAware.class);
						beanFactory.ignoreDependencyInterface(MessageSourceAware.class);
						beanFactory.ignoreDependencyInterface(ApplicationContextAware.class);
						beanFactory.ignoreDependencyInterface(EnvironmentAware.class);

						// BeanFactory interface not registered as resolvable type in a plain factory.
						// MessageSource registered (and found for autowiring) as a bean.
						beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
						beanFactory.registerResolvableDependency(ResourceLoader.class, this);
						beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
						beanFactory.registerResolvableDependency(ApplicationContext.class, this);
						//no MessageSource, because it is registered as a bean

						// Detect a LoadTimeWeaver and prepare for weaving, if found.
						if (beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)) {
							beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));
							// Set a temporary ClassLoader for type matching.
							beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));
						}

						// Register default environment beans.
						if (!beanFactory.containsLocalBean(ENVIRONMENT_BEAN_NAME)) {
							beanFactory.registerSingleton(ENVIRONMENT_BEAN_NAME, getEnvironment());
						}
						if (!beanFactory.containsLocalBean(SYSTEM_PROPERTIES_BEAN_NAME)) {
							beanFactory.registerSingleton(SYSTEM_PROPERTIES_BEAN_NAME, getEnvironment().getSystemProperties());
						}
						if (!beanFactory.containsLocalBean(SYSTEM_ENVIRONMENT_BEAN_NAME)) {
							beanFactory.registerSingleton(SYSTEM_ENVIRONMENT_BEAN_NAME, getEnvironment().getSystemEnvironment());
						}
					}

					// Allows post-processing of the bean factory in context subclasses.
					postProcessBeanFactory(beanFactory)(AbstractRefreshableWebApplicationContext){
						addBeanPostProcessor(new ServletContextAwareProcessor(this.servletContext, this.servletConfig));
					}

					// create BeanFactoryPostProcessor via getBean
					// Invoke factory processors registered as beans in the context.
					invokeBeanFactoryPostProcessors(beanFactory)(AbstractApplicationContext){
						PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());{
							if (beanFactory instanceof BeanDefinitionRegistry){
								convert beanFactory to registry 
								//the beanFactoryPostProcessors in AbstractApplicationContext is empty at the beginning
								//but if there are some BeanDefinitionRegistryPostProcessors in AbstractApplicationContext, 
								//it should not in BeanDefinitionRegistry and SingletonBeanRegistry otherwise it will be called twice!!!
								splite beanFactoryPostProcessors into registryPostProcessors and regularPostProcessors and deal with BeanDefinitionRegistryPostProcessors first:
								registryPostProcessor.postProcessBeanDefinitionRegistry(registry)
								
								//do not introduce the BeanDefinitionRegistryPostProcessor in the same group, otherwise it will be processed as the next group
								get all BeanDefinitionRegistryPostProcessors and spilt them to three groups:
								
								beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);{
									if (!isConfigurationFrozen()  || type == null || !allowEagerInit) {
										return doGetBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit){
											// Check all bean definitions.
											
											// Check manually registered singletons.
										}
									}
									//check the cache and then call doGetBeanNamesForType and cache the result
								}
								if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
									priorityOrderedPostProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
									processedBeans.add(ppName);
								}
								OrderComparator.sort(priorityOrderedPostProcessors);
								registryPostProcessors.addAll(priorityOrderedPostProcessors);
								invokeBeanDefinitionRegistryPostProcessors(priorityOrderedPostProcessors, registry);
								
								beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
								if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)) {
									orderedPostProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
									processedBeans.add(ppName);
								}
								OrderComparator.sort(orderedPostProcessors);
								registryPostProcessors.addAll(orderedPostProcessors);
								invokeBeanDefinitionRegistryPostProcessors(orderedPostProcessors, registry);
								
								//the rest
								while (reiterate) {
									reiterate = false;
									postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
									for (String ppName : postProcessorNames) {
										if (!processedBeans.contains(ppName)) {
											BeanDefinitionRegistryPostProcessor pp = beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class);
											registryPostProcessors.add(pp);
											processedBeans.add(ppName);
											pp.postProcessBeanDefinitionRegistry(registry);
											reiterate = true;
										}
									}
								}
								
								// private invokeBeanFactoryPostProcessors of this delegate
								invokeBeanFactoryPostProcessors(registryPostProcessors, beanFactory);
								invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);//regularPostProcessors may be empty
							}else{
								invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
							}
							
							beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);
							skip the processedBeans and spilt them into three groups:
							OrderComparator.sort(priorityOrderedPostProcessors);
							invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory);
							OrderComparator.sort(orderedPostProcessors);
							invokeBeanFactoryPostProcessors(orderedPostProcessors, beanFactory);
							invokeBeanFactoryPostProcessors(nonOrderedPostProcessors, beanFactory);
						}
					}
					
					// create BeanPostProcessor via getBean
					// Register bean processors that intercept bean creation.
					registerBeanPostProcessors(beanFactory){
						PostProcessorRegistrationDelegate.registerBeanPostProcessors{
							postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);
							int beanProcessorTargetCount = beanFactory.getBeanPostProcessorCount() + 1 + postProcessorNames.length;
							beanFactory.addBeanPostProcessor(new BeanPostProcessorChecker(beanFactory, beanProcessorTargetCount));{	
								this.beanPostProcessors.remove(beanPostProcessor);
								this.beanPostProcessors.add(beanPostProcessor);
							}
		
							spilt them into three groups and if it is MergedBeanDefinitionPostProcessor then internalPostProcessors.add(pp);
							OrderComparator.sort(priorityOrderedPostProcessors);
							registerBeanPostProcessors(beanFactory, priorityOrderedPostProcessors);
							OrderComparator.sort(orderedPostProcessors);
							registerBeanPostProcessors(beanFactory, orderedPostProcessors);
							registerBeanPostProcessors(beanFactory, nonOrderedPostProcessors);
							OrderComparator.sort(internalPostProcessors);
							registerBeanPostProcessors(beanFactory, internalPostProcessors);//why register them twice ??? see addBeanPostProcessor
							
							beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(applicationContext));
						}
					}

					// Initialize message source for this context.
					initMessageSource();

					// Initialize event multicaster for this context.
					initApplicationEventMulticaster();

					// Initialize other special beans in specific context subclasses.
					onRefresh();(used for extension)

					// Check for listener beans and register them.
					registerListeners();

					// Instantiate all remaining (non-lazy-init) singletons.
					finishBeanFactoryInitialization(beanFactory){
						// Initialize conversion service for this context.
						if (beanFactory.containsBean(CONVERSION_SERVICE_BEAN_NAME) &&
								beanFactory.isTypeMatch(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class)) {
							beanFactory.setConversionService(
									beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class));
						}

						// Initialize LoadTimeWeaverAware beans early to allow for registering their transformers early.
						String[] weaverAwareNames = beanFactory.getBeanNamesForType(LoadTimeWeaverAware.class, false, false);
						for (String weaverAwareName : weaverAwareNames) {
							getBean(weaverAwareName);
						}

						// Stop using the temporary ClassLoader for type matching.
						beanFactory.setTempClassLoader(null);

						// Allow for caching all bean definition metadata, not expecting further changes.
						beanFactory.freezeConfiguration();

						// Instantiate all remaining (non-lazy-init) singletons.
						beanFactory.preInstantiateSingletons(){
							RootBeanDefinition bd = getMergedLocalBeanDefinition
							if (!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()) {
							if (isFactoryBean(beanName)) {
								boolean isEagerInit = AccessController.doPrivileged 
								if (isEagerInit) {
									getBean(beanName);
								}
							}else{
								getBean(AbstractBeanFactory){
									if no bean definition will throw NoSuchBeanDefinitionException!!!
									doGetBean(AbstractBeanFactory){
										// call two kinds of getSingleton to get or to (create and register) the bean
										// EarlyBeanReference will be added in doCreateBean, and then BeanDefinitionValueResolver.resolveReference will call getSingleton to retrieve the EarlyBeanReference to solve the circle reference
										// Eagerly check singleton cache for manually registered singletons.
										// getSingleton has two versions, the other one should be provided with the objectFactory
										sharedInstance = getSingleton(beanName)(DefaultSingletonBeanRegistry){
											//this is used for getting the early reference
											this.singletonObjects.get(beanName);
											this.earlySingletonObjects.get(beanName);
											if allowEarlyReference {
												singletonObject = this.singletonFactories.get(beanName).getObject();
												this.earlySingletonObjects.put(beanName, singletonObject);
												this.singletonFactories.remove(beanName);
											}
										}
										if (sharedInstance != null && args == null) {
											getObjectForBeanInstance(AbstractBeanFactory){
												if not a FactoryBean return bean else
												getObjectFromFactoryBean(FactoryBeanRegistrySupport){
													/** Cache of singleton objects created by FactoryBeans: FactoryBean name --> object */
													Object object = this.factoryBeanObjectCache.get(beanName);
													object = doGetObjectFromFactoryBean(FactoryBeanRegistrySupport){
														if (System.getSecurityManager() != null) {
															 AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
																@Override
																public Object run() throws Exception {
																		return factory.getObject();
																	}
																}, acc);	
														}
														else {
															object = factory.getObject();
														}
														postProcessObjectFromFactoryBean(AbstractAutowireCapableBeanFactory){
															//may be wrapIfNecessary to create proxy
															applyBeanPostProcessorsAfterInitialization
														}
													}
													this.factoryBeanObjectCache.put
												}
											}
										}else {
											//check parent first!
											BeanFactory parentBeanFactory = getParentBeanFactory();
											if parentBeanFactory != null && !containsBeanDefinition(beanName)
												// Not found -> check parent.
												return parentBeanFactory.getBean
											
											markBeanAsCreated(beanName)
											
											String[] dependsOn = mbd.getDependsOn()
											for (String dependsOnBean : dependsOn) {
												//dependentBean depends on bean
												//will also check the transitiveDependency
												if (isDependent(beanName, dependsOnBean)) {
													throw new BeanCreationException("Circular depends-on relationship between '" +
															beanName + "' and '" + dependsOnBean + "'");
												}
												registerDependentBean(dependsOnBean, beanName);
												getBean(dependsOnBean);
											}
											
											if (mbd.isSingleton()) {
												sharedInstance = getSingleton(beanName, new ObjectFactory<Object>() {
													
													public Object getObject() throws BeansException {
														try {
															return createBean(beanName, mbd, args);	
														}
														catch (BeansException ex) {
															// Explicitly remove instance from singleton cache: It might have been put there
															// eagerly by the creation process, to allow for circular reference resolution.
															// Also remove any beans that received a temporary reference to the bean.
															destroySingleton(beanName);
															throw ex;
														}
													}
												}){
													Object singletonObject = this.singletonObjects.get(beanName);
													if (singletonObject == null) {
														beforeSingletonCreation -> singletonsCurrentlyInCreation.add
														singletonObject = singletonFactory.getObject();
														afterSingletonCreation -> singletonsCurrentlyInCreation.remove
														addSingleton(beanName, singletonObject)
													}
												}
												bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
											}else if (mbd.isPrototype()) {
												createBean
												getObjectForBeanInstance
											}else{ //other scope registered
												createBean
												getObjectForBeanInstance
											}
										}
										getTypeConverter().convertIfNecessary(bean, requiredType);
									}
								}
							}
							}
						}
					}

					// Last step: publish corresponding event.
					finishRefresh();
		
					no resourceLoader? because AbstractApplicationContext extends DefaultResourceLoader!
				}
			}
		}
	}
	
	parseDefaultElement(DefaultBeanDefinitionDocumentReader){												
		importBeanDefinitionResource(DefaultBeanDefinitionDocumentReader);

		processAliasRegistration(DefaultBeanDefinitionDocumentReader);
	
		processBeanDefinition(DefaultBeanDefinitionDocumentReader){
			BeanDefinitionHolder bdHolder = parseBeanDefinitionElement(BeanDefinitionParserDelegate){
				
			}
			BeanDefinitionReaderUtils.registerBeanDefinition(bdHolder, getReaderContext().getRegistry()){
				Registry is actually DefaultListableBeanFactory!!!
				registerBeanDefinition(DefaultListableBeanFactory)
			
			}
		}
	
		// recurse
		doRegisterBeanDefinitions(DefaultBeanDefinitionDocumentReader);
	}
	parseCustomElement(BeanDefinitionParserDelegate){
		//here to deal with "context:component-scan" 
		NamespaceHandler handler = this.readerContext.getNamespaceHandlerResolver().resolve(namespaceUri){
			resolve function will put the related parsers into handlerMappings of DefaultNamespaceHandlerResolver
			//map namespaceUri to handlerClass from property file
			NamespaceHandler namespaceHandler = (NamespaceHandler) BeanUtils.instantiateClass(handlerClass);
			namespaceHandler.init();//important
			handlerMappings.put(namespaceUri, namespaceHandler);
			return namespaceHandler;
		}
		handler.parse(NamespaceHandlerSupport){
			? extends BeanDefinitionParser parser = findParserForElement{
				String localName = parserContext.getDelegate().getLocalName(element);
				BeanDefinitionParser parser = this.parsers.get(localName);
			}
			return parser.parse();
		}
	}
	ComponentScanBeanDefinitionParser.parse(){
		configureScanner(ComponentScanBeanDefinitionParser)
		doScan(ClassPathBeanDefinitionScanner){
			Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<BeanDefinitionHolder>();
			Set<BeanDefinition> candidates = findCandidateComponents(ClassPathScanningCandidateComponentProvider){
				Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
				Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
				for (Resource resource : resources) {
					if resource.isReadable{
					MetadataReader has visitor for AnnotationMetadata ???
					MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource){
						new SimpleMetadataReader(resource, this.resourceLoader.getClassLoader()){
							classReader = new ClassReader(is);
							AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor(classLoader);
							//read this accept !!! how to get all metadata
							classReader.accept(visitor, ClassReader.SKIP_DEBUG){
								readAnnotationValues(visitor.visitAnnotation){
									readAnnotationValue(...,av)(ClassReader)
									av.visitEnd()(RecursiveAnnotationAttributesVisitor){
										doVisitEnd(AnnotationAttributesReadingVisitor){
											super.doVisitEnd//setup AnnotationAttributes
											setup attributesMap and metaAnnotationMap
										}
									}
								}
							}

							this.annotationMetadata = visitor;
							this.classMetadata = visitor;
							this.resource = resource;
						}
					}
					//TypeFilter will determine the candidate: matchSelf of AnnotationTypeFilter will check annotationSet and metaAnnotationMap of AnnotationMetadataReadingVisitor
					//sth below matchself in match function ???
					//isConditionMatch to use @Conditional
					if isCandidateComponent(metadataReader){
						ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
						sbd.setResource(resource);
						sbd.setSource(resource);
						if (isCandidateComponent(sbd))//verify whether sbd is concrete and independent !!!
							candidates.add(sbd);
					}
					}
				}
				return candidates;
			}
			for (BeanDefinition candidate : candidates) {
				//@Scope to make scoped-proxy with proxyMode or other scopes with value
				ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
				candidate.setScope(scopeMetadata.getScopeName());
				//get the bean name
				String beanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
				if (candidate instanceof AbstractBeanDefinition) {
					postProcessBeanDefinition((AbstractBeanDefinition) candidate, beanName);{
						beanDefinition.applyDefaults(this.beanDefinitionDefaults);
						beanDefinition.setAutowireCandidate(PatternMatchUtils.simpleMatch(this.autowireCandidatePatterns, beanName));
					}
				}
				if (candidate instanceof AnnotatedBeanDefinition) {
					AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);{
						deal with @Primary, @Lazy, @DependsOn, @Role
					}
				}
				if (checkCandidate(beanName, candidate)){// please check condition ???
					BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
					definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
					beanDefinitions.add(definitionHolder);
					registerBeanDefinition(definitionHolder, this.registry);
				}
			}
			return beanDefinitions;
		}
		registerComponents(ComponentScanBeanDefinitionParser){
			compositeDef.addNestedComponent(new BeanComponentDefinition(beanDefHolder));//no effect
			if annotationConfig{
				AnnotationConfigUtils.registerAnnotationConfigProcessors
				compositeDef.addNestedComponent(new BeanComponentDefinition(processorDefinition));//no effect
			}
			readerContext.fireComponentRegistered(compositeDef);//do nothing!!!
		}
	}
	AspectJAutoProxyBeanDefinitionParser.parse(){
	}
	ConfigBeanDefinitionParser.parse(){
		to create DefaultBeanFactoryPointcutAdvisor and AspectJPointcutAdvisor advisors
	}
	AnnotationDrivenBeanDefinitionParser.parse(){
		if ("aspectj".equals(mode)) {
			registerTransactionAspect(element, parserContext);
		}
		else {
			// BeanFactoryTransactionAttributeSourceAdvisor has TransactionAttributeSourcePointcut
			// TransactionAttributeSourcePointcut will match whether the target has @Transactional
			AopAutoProxyConfigurer.configureAutoProxyCreator(element, parserContext);{
				AopNamespaceUtils.registerAutoProxyCreatorIfNecessary(parserContext, element);{
					BeanDefinition beanDefinition = AopConfigUtils.registerAutoProxyCreatorIfNecessary(
							parserContext.getRegistry(), parserContext.extractSource(sourceElement));{
								//actually did not register or escalate and return null;
								return registerOrEscalateApcAsRequired(InfrastructureAdvisorAutoProxyCreator.class, registry, source);
							}
					//if proxyTargetClass is true
					useClassProxyingIfNecessary(parserContext.getRegistry(), sourceElement);
					//if beanDefinition is null, did not register it
					registerComponentIfNecessary(beanDefinition, parserContext);
				}
				//txAdvisorBeanName = "org.springframework.transaction.config.internalTransactionAdvisor"
				if (!parserContext.getRegistry().containsBeanDefinition(txAdvisorBeanName)) {
					// Create the TransactionAttributeSource definition.
					RootBeanDefinition sourceDef = new RootBeanDefinition(
						"org.springframework.transaction.annotation.AnnotationTransactionAttributeSource");
					setup sourceDef
					String sourceName = parserContext.getReaderContext().registerWithGeneratedName(sourceDef);
					
					// Create the TransactionInterceptor definition.
					RootBeanDefinition interceptorDef = new RootBeanDefinition(TransactionInterceptor.class);
					setup interceptorDef
					interceptorDef.getPropertyValues().add("transactionManagerBeanName","transactionManager");
					interceptorDef.getPropertyValues().add("transactionAttributeSource", new RuntimeBeanReference(sourceName));
					//interceptorName "packageName.TransactionInterceptor#0"
					String interceptorName = parserContext.getReaderContext().registerWithGeneratedName(interceptorDef);
					
					// Create the TransactionAttributeSourceAdvisor definition.
					RootBeanDefinition advisorDef = new RootBeanDefinition(BeanFactoryTransactionAttributeSourceAdvisor.class);
					setup advisorDef
					advisorDef.getPropertyValues().add("transactionAttributeSource", new RuntimeBeanReference(sourceName));
					//will then retrieve by getBean("adviceBeanName") of getAdvice
					advisorDef.getPropertyValues().add("adviceBeanName", interceptorName);
					if hasAttribute("order")
						advisorDef.getPropertyValues().add("order", element.getAttribute("order"));
					parserContext.getRegistry().registerBeanDefinition(txAdvisorBeanName, advisorDef);
					
					//put component into compDefinition
					parserContext.registerComponent
				}
			}
		}
	}
	AbstractBeanDefinitionParser.parse(){
		AbstractSingleBeanDefinitionParser.parseInternal(){
			...
			Class<?> beanClass = getBeanClass(element);//tx:advice return TransactionInterceptor.class
			if (beanClass != null) {
				builder.getRawBeanDefinition().setBeanClass(beanClass);
			}
			TxAdviceBeanDefinitionParser.doParse(){
				...
				parseAttributeSource(){
					...
					RootBeanDefinition attributeSourceDefinition = new RootBeanDefinition(NameMatchTransactionAttributeSource.class);
					attributeSourceDefinition.setSource(parserContext.extractSource(attrEle));
					attributeSourceDefinition.getPropertyValues().add("nameMap", transactionAttributeMap);
					return attributeSourceDefinition;
				}
				...
			}
			...
		}
		String id = resolveId(element, definition, parserContext);//tx:advice return transactionAdvice
	}
	AnnotationDrivenBeanDefinitionParser.parse(){
		//compDefinition's name is mvc:annotation-driven
		parserContext.pushContainingComponent(compDefinition);
		
		//getRegistry().registerBeanDefinition
		parserContext.getReaderContext().registerWithGeneratedName
		
		//put component into compDefinition
		parserContext.registerComponent
	}
	createBean(beanName, mbd, args)(AbstractAutowireCapableBeanFactory){
		creates a bean instance, populates the bean instance, applies post-processors, etc
		
		// Make sure bean class is actually resolved at this point. resolve class name to real class object
		resolveBeanClass(mbd, beanName);
		
		//method look-up overrides
		mbd.prepareMethodOverrides();

		// Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.
		bean = resolveBeforeInstantiation(AbstractAutowireCapableBeanFactory){
			bean = applyBeanPostProcessorsBeforeInstantiation(mbd.getBeanClass(), beanName);
			if (bean != null) {
				bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);(AbstractAutowireCapableBeanFactory)
			}
		}
		if (bean != null) {
			return bean;
		}
		doCreateBean(AbstractAutowireCapableBeanFactory){
			/** Cache of unfinished FactoryBean instances: FactoryBean name --> BeanWrapper */
			instanceWrapper = this.factoryBeanInstanceCache.remove(beanName);
			instanceWrapper = createBeanInstance(beanName, mbd, args){
				//using an appropriate instantiation strategy:factory method, constructor autowiring, or simple instantiation
				
				// Make sure bean class is actually resolved at this point.
				Class<?> beanClass = resolveBeanClass(mbd, beanName);
				
				//normally, args is null, please notice the condition of different methods to be called
				if (mbd.getFactoryMethodName() != null)
					instantiateUsingFactoryMethod(beanName, mbd, args){
						return new ConstructorResolver(this).instantiateUsingFactoryMethod(beanName, mbd, explicitArgs){
							//too complex ??? 
						}
					}
				
				// Shortcut when re-creating the same bean... (prototype or other scopes)
				boolean resolved = false;
				boolean autowireNecessary = false;
				if (args == null) {//explicit args is null so use constructor argument values from bean definition
					synchronized (mbd.constructorArgumentLock) {
						if (mbd.resolvedConstructorOrFactoryMethod != null) {
							resolved = true;
							autowireNecessary = mbd.constructorArgumentsResolved;
						}
					}
				}
				if (resolved) {
					if (autowireNecessary) {
						return autowireConstructor(beanName, mbd, null, null);
					}
					else {
						return instantiateBean(beanName, mbd);
					}
				}
				
				//Determine candidate constructors to use, checking all registered BeanPostProcessors
				Constructor<?>[] ctors = determineConstructorsFromBeanPostProcessors(beanClass, beanName);{
					ctors = SmartInstantiationAwareBeanPostProcessor.determineCandidateConstructors(beanClass, beanName);
					if (ctors != null) {
						return ctors;
					}//maybe a bug
				}
				
				//autowire constructors conditions !!!
				if (ctors != null ||
				mbd.getResolvedAutowireMode() == RootBeanDefinition.AUTOWIRE_CONSTRUCTOR ||
				mbd.hasConstructorArgumentValues() || !ObjectUtils.isEmpty(args)){
					return autowireConstructor(beanName, mbd, ctors, args){
						new ConstructorResolver(this).autowireConstructor(beanName, mbd, ctors, explicitArgs){
							//too complex ??? 
							//it will resovle the args and convert it!!!
							BeanWrapperImpl bw = new BeanWrapperImpl();
							
							if (explicitArgs != null) {
								argsToUse = explicitArgs;
							}
							else {
								Object[] argsToResolve = null;
								synchronized (mbd.constructorArgumentLock) {
									constructorToUse = (Constructor<?>) mbd.resolvedConstructorOrFactoryMethod;
									if (constructorToUse != null && mbd.constructorArgumentsResolved) {
										// Found a cached constructor...
										argsToUse = mbd.resolvedConstructorArguments;
										if (argsToUse == null) {
											argsToResolve = mbd.preparedConstructorArguments;
										}
									}
								}
								if (argsToResolve != null) {
									argsToUse = resolvePreparedArguments(beanName, mbd, bw, constructorToUse, argsToResolve);{
										... ???
										BeanDefinitionValueResolver.resolveValueIfNecessary
									}
								}
							}
							
							//ctors is null for not autowireanotation and explicitArgs is allways null
							if (constructorToUse == null) {
								//determine the constructor to use, too complex ???
								minNrOfArgs = resolveConstructorArguments(beanName, mbd, bw, cargs, resolvedValues){
										//Resolve the constructor arguments for this bean.
										//This may involve looking up other beans.
										BeanDefinitionValueResolver.resolveValueIfNecessary
								}
								... ???
							}
							
							beanInstance = this.beanFactory.getInstantiationStrategy().instantiate(mbd, beanName, this.beanFactory, constructorToUse, argsToUse);
							bw.setWrappedInstance(beanInstance);
							return bw;
						}
					}
				}
				
				return instantiateBean(beanName, mbd){
					beanInstance = getInstantiationStrategy().instantiate(mbd, beanName, parent);
					BeanWrapper bw = new BeanWrapperImpl(beanInstance);
					initBeanWrapper(bw){
						bw.setConversionService(getConversionService());
						registerCustomEditors(bw);
					}
					return bw;
				}
			}
			
			Object bean = instanceWrapper.getWrappedInstance();
			applyMergedBeanDefinitionPostProcessors(AbstractAutowireCapableBeanFactory)
			
			boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences && isSingletonCurrentlyInCreation(beanName));
			if earlySingletonExposure{
				addSingletonFactory(beanName, new ObjectFactory<Object>() {
					@Override
					public Object getObject() throws BeansException {
						return getEarlyBeanReference(beanName, mbd, bean){
							ibp.getEarlyBeanReference
							ibp is a SmartInstantiationAwareBeanPostProcessor and should be under AbstractAutoProxyCreator!!!
						}
					}
				});
			}
			Object exposedObject = bean;
			//setup all the properties
			populateBean(beanName, mbd, instanceWrapper);
			
			//exposedObject may be changed to a proxy
			exposedObject = initializeBean(beanName, exposedObject, mbd);
			
			//if the bean is early referenced and it is a proxy, this will get the proxy.
			if (earlySingletonExposure){
				Object earlySingletonReference = getSingleton(beanName, false);
				if (earlySingletonReference != null) {
					if (exposedObject == bean) {
						exposedObject = earlySingletonReference;
					}
				}else if ???
				
			}
			
			registerDisposableBeanIfNecessary(beanName, bean, mbd);{
				//do not care about prototype
				if (!mbd.isPrototype() && requiresDestruction(bean, mbd)) {
					if (mbd.isSingleton()) {
						this.disposableBeans.put(beanName, bean);(DefaultSingletonBeanRegistry)
					}else{
						// A bean with a custom scope...
						scope.registerDestructionCallback
					}
				}
			}
		}
	}
	populateBean(beanName, mbd, instanceWrapper){
		PropertyValues pvs = mbd.getPropertyValues();

		if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
			apply InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation
		}

		if (RootBeanDefinition.AUTOWIRE_BY_NAME || RootBeanDefinition.AUTOWIRE_BY_TYPE) {
			//MutablePropertyValues implements PropertyValues
			//MutablePropertyValues.addPropertyValue will replace the original PropertyValue or merge it
			//pvs itself is MutablePropertyValues
			MutablePropertyValues newPvs = new MutablePropertyValues(pvs);
			// Add property values based on autowire by name if applicable.
			if (mbd.getResolvedAutowireMode() == RootBeanDefinition.AUTOWIRE_BY_NAME) {
				autowireByName(beanName, mbd, bw, newPvs);{
					unsatisfiedNonSimpleProperties{
						PropertyValues pvs = mbd.getPropertyValues();
						PropertyDescriptor[] pds = bw.getPropertyDescriptors();
						for (PropertyDescriptor pd : pds) {
							//autowire candidate conditions:
							//has writer, no dep check, not contained in PropertyValues, not simple type
							if (pd.getWriteMethod() != null && !isExcludedFromDependencyCheck(pd) && !pvs.contains(pd.getName()) &&
									!BeanUtils.isSimpleProperty(pd.getPropertyType())) {
								result.add(pd.getName());
							}
						}
					}
				}
			}
			// Add property values based on autowire by type if applicable.
			if (mbd.getResolvedAutowireMode() == RootBeanDefinition.AUTOWIRE_BY_TYPE) {
				autowireByType(beanName, mbd, bw, newPvs);{
					unsatisfiedNonSimpleProperties{}
					resolveDependency{}
				}
			}
			pvs = newPvs;
		}
		
		if (hasInstAwareBpps) {
			apply InstantiationAwareBeanPostProcessor.postProcessPropertyValues
		}
		if (needsDepCheck) {
			checkDependencies(beanName, mbd, filteredPds, pvs);
		}
		
		applyPropertyValues{
			if (pvs == null || pvs.isEmpty()) {
				return;
			}
			
			if (pvs instanceof MutablePropertyValues) {
				List<PropertyValue> original = mpvs.getPropertyValueList();
			else {
				List<PropertyValue> original = Arrays.asList(pvs.getPropertyValues());
			}
			List<PropertyValue> deepCopy = new ArrayList<PropertyValue>(original.size());
			for (PropertyValue pv : original) {
				Object originalValue = pv.getValue();
				//resolveValueIfNecessary will deal with array,list,set,map,property and so on
				Object resolvedValue = valueResolver.resolveValueIfNecessary(pv, originalValue);{
					//do not mind this part any more
					//VALUE_TYPE_ATTRIBUTE will define the element_type of ManagedXXXX, see parseXXXXElement of BeanDefinitionParserDelegate
					//resolveManagedArray will return element_type array while the other resolveManagedXXXX will return Object_type container
					//evaluate function can resolve Spell expression
				}
				Object convertedValue = resolvedValue;
				boolean convertible = bw.isWritableProperty(propertyName) &&
						!PropertyAccessorUtils.isNestedOrIndexedProperty(propertyName);
				if convertible
					//call typeConverter to convert the value
					convertedValue = convertForProperty(resolvedValue, propertyName, bw, converter);
				//avoid re-conversion
				if (resolvedValue == originalValue) {
					if (convertible) {
						pv.setConvertedValue(convertedValue);
					}
					deepCopy.add(pv);
				}
				else if (convertible && originalValue instanceof TypedStringValue &&
						!((TypedStringValue) originalValue).isDynamic() &&
						!(convertedValue instanceof Collection || ObjectUtils.isArray(convertedValue))) {
					pv.setConvertedValue(convertedValue);
					deepCopy.add(pv);
				}
				else {
					resolveNecessary = true;
					deepCopy.add(new PropertyValue(pv, convertedValue));
				}
			}
			if (mpvs != null && !resolveNecessary) {
				mpvs.setConverted();
			}
			bw.setPropertyValues(new MutablePropertyValues(deepCopy)){
				for (PropertyValue pv : propertyValues) {
					setPropertyValue(PropertyTokenHolder tokens, PropertyValue pv)(BeanWrapperImpl){
						//keys are abc,bcd from property name of props['abc']['bcd']
						if (tokens.keys != null) {
							...???
						}else{
							//no writable method found, NotWritablePropertyException!!!
							//PropertyMatches class to find the possible property!!!
							get the PropertyDescriptor to get the WriteMethod{
								getWriteMethod(){
									writeMethodName = Introspector.SET_PREFIX + getBaseName();//NameGenerator.capitalize(getName()); make the initial character capital
									writeMethod = Introspector.findMethod(cls, writeMethodName, 1, args);
								}
							}
							if (pv.isConverted()) {
								valueToApply = pv.getConvertedValue();
							} else {
								get the oldValue from readMethod 
								valueToApply = convertForProperty(propertyName, oldValue, originalValue, pd,...);
							}
							get the writeMethod and invoke with valueToApply
						}
					}
				}
			}
		}
	}
	initializeBean(beanName, exposedObject, mbd){
		invokeAwareMethods
		applyBeanPostProcessorsBeforeInitialization
		invokeInitMethods{
			afterPropertiesSet(InitializingBean)
			invokeCustomInitMethod
		}
		applyBeanPostProcessorsAfterInitialization
	}
	//if require type is int, it will return Integer
	//value is always String, parsed by beanDefinitionParser or AutowiredAnnotationBeanPostProcessor
	//convertIfNecessary has three format
	TypeConverterSupport.convertIfNecessary(){
		doConvert(Object value, Class<T> requiredType, MethodParameter methodParam, Field field){
			if field is not null
			return this.typeConverterDelegate.convertIfNecessary(value, requiredType, field){
				return convertIfNecessary(null, null, value, requiredType,
				(field != null ? new TypeDescriptor(field) : TypeDescriptor.valueOf(requiredType)));{
					//convertedValue is the value to be converted
					PropertyEditor editor = this.propertyEditorRegistry.findCustomEditor(requiredType, propertyName);
					ConversionFailedException firstAttemptEx = null;
					ConversionService conversionService = this.propertyEditorRegistry.getConversionService();
					// No custom editor but custom ConversionService specified
					if (editor == null && conversionService != null && convertedValue != null && typeDescriptor != null){
						return (T) conversionService.convert
					}
					
					//Value not of required type or has editor
					if (editor != null || (requiredType != null && !ClassUtils.isAssignableValue(requiredType, convertedValue)) ){
						if requiredType is Collection and convertedValue is String
							turn convertedValue into String array
						if (editor == null) {
							editor = findDefaultEditor(requiredType);{
								if (requiredType != null) {
									// No custom editor -> check BeanWrapperImpl's default editors.
									editor = this.propertyEditorRegistry.getDefaultEditor(requiredType);//type mapped to editor
									if (editor == null && !String.class.equals(requiredType)) {
										// No BeanWrapper default editor -> check standard JavaBean editor.
										editor = BeanUtils.findEditorByConvention(requiredType);{
											String editorName = targetType.getName() + "Editor";
											Class<?> editorClass = cl.loadClass(editorName);
											if (!PropertyEditor.class.isAssignableFrom(editorClass)) {
												unknownEditorTypes.put(targetType, Boolean.TRUE);
												return null;
											}
											return (PropertyEditor) instantiateClass(editorClass);
										}
									}
								}
							}
						}
						convertedValue = doConvertValue(oldValue, convertedValue, requiredType, editor){
							
							deal with situation convertedValue is not a String(no return statement)
							
							if convertedValue instanceof String{
								if (editor != null) {
									String newTextValue = (String) convertedValue;
									return doConvertTextValue(oldValue, newTextValue, editor);{
										editor.setValue(oldValue);
										editor.setAsText(newTextValue);
										return editor.getValue();
									}
								}
								else if (String.class.equals(requiredType)) {
									returnValue = convertedValue;
								}
							}
							return returnValue;							
						}
					}
					//always skipp the following rules
					if (requiredType != null) {
						// Try to apply some standard type conversion rules if appropriate.
						if (convertedValue != null) {
							if (Object.class.equals(requiredType)) {
								return (T) convertedValue;
							}
							if (requiredType.isArray()) {
								convertToTypedArray {
									get componentType, no need to call getElementTypeDescriptor
								}
							}else if (convertedValue instanceof Collection) {
								convertToTypedCollection  {
									TypeDescriptor elementType = typeDescriptor.getElementTypeDescriptor();{
										ResolvableType.getGenerics();
									}
									Iterator<?> it = original.iterator();
									for (; it.hasNext(); i++) {
										String indexedPropertyName = buildIndexedPropertyName(propertyName, i);
										Object convertedElement = convertIfNecessary(indexedPropertyName, null, element,
											(elementType != null ? elementType.getType() : null) , elementType);
									}
							}else if (convertedValue instanceof Map) {
								convertToTypedMap {
									similar to convertToTypedCollection
								}
							}
							if (convertedValue.getClass().isArray() && Array.getLength(convertedValue) == 1) {
								convertedValue = Array.get(convertedValue, 0);
							}
							if (String.class.equals(requiredType) && ClassUtils.isPrimitiveOrWrapper(convertedValue.getClass())) {
								// We can stringify any primitive value...
								return (T) convertedValue.toString();
							}
							else if (convertedValue instanceof String && !requiredType.isInstance(convertedValue)) {
								if (firstAttemptEx == null && !requiredType.isInterface() && !requiredType.isEnum()) {
									Constructor<T> strCtor = requiredType.getConstructor(String.class);
									return BeanUtils.instantiateClass(strCtor, convertedValue);//why return string object ???
								}
								String trimmedValue = ((String) convertedValue).trim();
								if (requiredType.isEnum() && "".equals(trimmedValue)) {
									// It's an empty enum identifier: reset the enum value to null.
									return null;
								}
								convertedValue = attemptToConvertStringToEnum(requiredType, trimmedValue, convertedValue);
							}
						}
					}
					return (T) convertedValue;
				}
			}
			else
			return this.typeConverterDelegate.convertIfNecessary(value, requiredType, methodParam);
		}
	}
	
	
// Destroy start here.............

    public void contextDestroyed ( ServletContextEvent sce );
		closeWebApplicationContext(from ContextLoader){
			((ConfigurableWebApplicationContext) this.context).close(){
				doClose(){
					// Publish shutdown event.
					publishEvent(new ContextClosedEvent(this));
					
					// Stop all Lifecycle beans, to avoid delays during individual destruction.
					getLifecycleProcessor().onClose();
					
					// Destroy all cached singletons in the context's BeanFactory.
					destroyBeans();

					// Close the state of this context itself.
					closeBeanFactory();

					// Let subclasses do some final clean-up if they wish...
					onClose();
				}
				Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
			}
			...
		}

		
/** Resolver to use for checking if a bean definition is an autowire candidate */
private AutowireCandidateResolver autowireCandidateResolver = new SimpleAutowireCandidateResolver();

QualifierAnnotationAutowireCandidateResolver deal with Value and Qualifier, addQualifierType to add the other annotation type

resolveDependency is the important function for autowire

AutowiredAnnotationBeanPostProcessor: (Value & Autowired)
	//used by autowireConstructor of AbstractAutowireCapableBeanFactory, autowireConstructor use ConstructorResolver to find the constructor
	determineCandidateConstructors(Class<?> beanClass, String beanName){
		//there must be one requiredConstructor and one defaultConstructor
		AnnotationAttributes annotation = findAutowiredAnnotation(candidate){
			//autowiredAnnotationTypes has Autowired, Value and so on.
			for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
				AnnotationAttributes annotation = AnnotatedElementUtils.getAnnotationAttributes(candidate, type.getName());
				if (annotation != null) {
					return annotation;
				}
			}
			return null;
		}
		boolean required = determineRequiredStatus(annotation);
		
		candidateConstructors = candidates.toArray(new Constructor<?>[candidates.size()]);
		return candidateConstructors;
	}
	
	postProcessMergedBeanDefinition{
		InjectionMetadata metadata = findAutowiringMetadata(beanName, beanType){
			String cacheKey = (StringUtils.hasLength(beanName) ? beanName : clazz.getName());
			InjectionMetadata metadata = this.injectionMetadataCache.get(cacheKey);
			if metadata == null{
				metadata = buildAutowiringMetadata(clazz){
					do {
						for (Field field : targetClass.getDeclaredFields()) {
							AnnotationAttributes annotation = findAutowiredAnnotation(field);
							if (Modifier.isStatic(field.getModifiers()))
								continue;
							boolean required = determineRequiredStatus(annotation);
							currElements.add(new AutowiredFieldElement(field, required));
						}
						for (Method method : targetClass.getDeclaredMethods()) {
							deal with bridge methods
							AnnotationAttributes annotation = findAutowiredAnnotation(method/bridgedMethod);
							if (annotation != null && method.equals(ClassUtils.getMostSpecificMethod(method, clazz))) {
								if (Modifier.isStatic(method.getModifiers()))
									continue;
								boolean required = determineRequiredStatus(annotation);
								PropertyDescriptor pd = BeanUtils.findPropertyForMethod(method){
									PropertyDescriptor[] pds = getPropertyDescriptors(method.getDeclaringClass());
									for (PropertyDescriptor pd : pds) {
										if (method.equals(pd.getReadMethod()) || method.equals(pd.getWriteMethod())) {
											return pd;
										}
									}
									return null;
								}
								currElements.add(new AutowiredMethodElement(method, required, pd));
							}
						}
						elements.addAll(0, currElements);
						targetClass = targetClass.getSuperclass();
					}while (targetClass != null && targetClass != Object.class);
					return new InjectionMetadata(clazz, elements);
				}
				this.injectionMetadataCache.put(cacheKey, metadata);
			}
		}
		//checkedElements will be injected later
		metadata.checkConfigMembers(beanDefinition);{
			for (InjectedElement element : this.injectedElements) {
			Member member = element.getMember();
			if (!beanDefinition.isExternallyManagedConfigMember(member)) {
				beanDefinition.registerExternallyManagedConfigMember(member);
				checkedElements.add(element);
			}
		}
		this.checkedElements = checkedElements;	
		}
	}
	
	postProcessPropertyValues{
		InjectionMetadata metadata = findAutowiringMetadata(beanName, bean.getClass());
		metadata.inject(bean, beanName, pvs){
			element.inject(target, beanName, pvs){
				AutowiredFieldElement{
					if (this.cached) {
						value = resolvedCachedArgument(beanName, this.cachedFieldValue){
							if (cachedArgument instanceof DependencyDescriptor) {
								DependencyDescriptor descriptor = (DependencyDescriptor) cachedArgument;
								return this.beanFactory.resolveDependency(descriptor, beanName, null, null);
							}
							else if (cachedArgument instanceof RuntimeBeanReference) {
								return this.beanFactory.getBean(((RuntimeBeanReference) cachedArgument).getBeanName());
							}
							else {
								return cachedArgument;
							}
						}
					}else {
						DependencyDescriptor desc = new DependencyDescriptor(field, this.required);
						//value can be bean, primitive value, DependencyObjectFactory and DependencyProvider
						value = beanFactory.resolveDependency(desc, beanName, autowiredBeanNames, typeConverter)(DefaultListableBeanFactory){
							if (descriptor.getDependencyType().equals(ObjectFactory.class)) {
								return new DependencyObjectFactory(descriptor, beanName);
							}
							else if (descriptor.getDependencyType().equals(javaxInjectProviderClass)) {
								return new DependencyProviderFactory().createDependencyProvider(descriptor, beanName);
							}
							else{
								Object result = getAutowireCandidateResolver().getLazyResolutionProxyIfNecessary(descriptor, beanName){
									if isLazy(descriptor){
										ContextAnnotationAutowireCandidateResolver.buildLazyResolutionProxy{
											TargetSource ts = new TargetSource() {
													@Override
													public Class<?> getTargetClass() {
														return descriptor.getDependencyType();
													}
													@Override
													public boolean isStatic() {
														return false;
													}
													@Override
													public Object getTarget() {
														return beanFactory.doResolveDependency(descriptor, beanName, null, null);
													}
													@Override
													public void releaseTarget(Object target) {
													}
											}
											ProxyFactory pf = new ProxyFactory();
											pf.setTargetSource(ts);
											Class<?> dependencyType = descriptor.getDependencyType();
											if (dependencyType.isInterface()) {
												pf.addInterface(dependencyType);
											}
											return pf.getProxy(beanFactory.getBeanClassLoader());
										}
									}
								}
								if result == null then
								doResolveDependency(descriptor, beanName, autowiredBeanNames, typeConverter){
									Object value = getAutowireCandidateResolver().getSuggestedValue(descriptor);
									
									if value not null{
										//Value does do the type conversion
										
										if value is String{
											String strVal = resolveEmbeddedValue((String) value);{
												for (StringValueResolver resolver : this.embeddedValueResolvers) {
													//PlaceholderResolvingStringValueResolver to resolve the placeholder
													result = resolver.resolveStringValue(result);
												}
												return result;
											}
											BeanDefinition bd = (beanName != null && containsBean(beanName) ? getMergedBeanDefinition(beanName) : null);
											value = evaluateBeanDefinitionString(value, bd)(AbstractBeanFactory){
												//call expression resolver to resolve the value to expression
												return evaluate(StandardBeanExpressionResolver){
													//SpelExpressionParser
													expr = this.expressionParser.parseExpression(value, this.beanExpressionParserContext);{
														if (context.isTemplate()) {
															//always go this way
															return parseTemplate(expressionString, context);
														}else {
															return doParseExpression(expressionString, context);
														}
													}
													this.expressionCache.put(value, expr);
													//several PropertyAccessor to get value from different source, BeanFactoryAccessor to get bean from the beanfactory
													setup the StandardEvaluationContext and put it into evaluationCache
													return expr.getValue(sec){
														too complex ???
														//CompoundExpression with PropertyOrFieldReference and Indexer for list,map,property: props['abc']
														//CompoundExpression with PropertyOrFieldReference and MethodReference for function calling: config.getTheTestIfExist('test')
													}
												}
											}
										}
										TypeConverter converter = (typeConverter != null ? typeConverter : getTypeConverter());
										converter.convertIfNecessary(...);
									}else{
										//Autowired does do type conversion in getBean
									
										if (type.isArray()) {
											//to find all the beans of componentType
											targetDesc.increaseNestingLevel();
											Map<String, Object> matchingBeans = findAutowireCandidates(beanName, componentType, targetDesc);
											if (this.dependencyComparator != null && result instanceof Object[]) {
												Arrays.sort((Object[]) result, this.dependencyComparator);
											}
										}else if (Collection.class.isAssignableFrom(type) && type.isInterface()) {
											//to find all the beans of elementType
											targetDesc.increaseNestingLevel();
											Map<String, Object> matchingBeans = findAutowireCandidates(beanName, elementType, targetDesc);
											if (this.dependencyComparator != null && result instanceof List) {
												Collections.sort((List<?>) result, this.dependencyComparator);
											}
										}else  if (Map.class.isAssignableFrom(type) && type.isInterface()) {
											//keyType should be String
											if (keyType == null || !String.class.isAssignableFrom(keyType)) {
												if (descriptor.isRequired()) {
													throw new FatalBeanException("Key type [" + keyType + "] of map [" + type.getName() +
															"] must be assignable to [java.lang.String]");
												}
												return null;
											}
											//to find all the beans of valueType
											targetDesc.increaseNestingLevel();
											Map<String, Object> matchingBeans = findAutowireCandidates(beanName, valueType, targetDesc);
										}else{
											Map<String, Object> matchingBeans = findAutowireCandidates(beanName, type, descriptor)(DefaultListableBeanFactory){
												candidateNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors
												
												//resolvableDependencies contains BeanFactory.class, ResourceLoader.class, ApplicationEventPublisher.class, ApplicationContext.class and so on
												for (Class<?> autowiringType : this.resolvableDependencies.keySet()) {
													if (autowiringType.isAssignableFrom(requiredType)) {
														retrieve the object from resolvableDependencies
														AutowireUtils.resolveAutowiringValue ???
													}
												}
											
												for (String candidateName : candidateNames) {
													//isAutowireCandidate will call AutowireCandidateResolver.isAutowireCandidate and then call AutowireCandidateResolver.checkQualifiers to deal with Qualifier.
													if (!candidateName.equals(beanName) && isAutowireCandidate(candidateName, descriptor)) {
														// getBean !!!
														result.put(candidateName, getBean(candidateName));
													}
												}
												
												//For a fallback match, the bean name is considered a default qualifier value.
												if (result.isEmpty()) {
													DependencyDescriptor fallbackDescriptor = descriptor.forFallbackMatch();
													for (String candidateName : candidateNames) {
														if (!candidateName.equals(beanName) && isAutowireCandidate(candidateName, fallbackDescriptor)) {
															result.put(candidateName, getBean(candidateName));
														}
													}
												}
											}
											if (matchingBeans.isEmpty()) {
												if (descriptor.isRequired()) {
													raiseNoSuchBeanDefinitionException(type, "", descriptor);
												}
												return null;
											}
											if (matchingBeans.size() > 1) {
												//primary autowireCandidate of AbstractBeanDefinition ???
												String primaryBeanName = determinePrimaryCandidate(matchingBeans, descriptor);
												autowiredBeanNames.add(primaryBeanName);
											}
											autowiredBeanNames.add;
											return the exactly one;
										}
									}
								}
							}
						}
						//setup cachedFieldValue
						if (value != null || this.required) {
							this.cachedFieldValue = desc;(DependencyDescriptor)
							registerDependentBeans(beanName, autowiredBeanNames);
							if (autowiredBeanNames.size() == 1) {
								String autowiredBeanName = autowiredBeanNames.iterator().next();
								if (beanFactory.containsBean(autowiredBeanName)) {
									if (beanFactory.isTypeMatch(autowiredBeanName, field.getType())) {
										this.cachedFieldValue = new RuntimeBeanReference(autowiredBeanName);
									}
								}
							}
						}						
					}
					if (value != null) {
						ReflectionUtils.makeAccessible(field);
						field.set(bean, value);
					}
				}
				AutowiredMethodElement{
					//deal with all the parameters in the way of dealing with AutowiredFieldElement
					if (this.cached) {
						arguments = resolveCachedArguments(beanName);//call resolvedCachedArgument
					}else{
						...
						DependencyDescriptor desc = new DependencyDescriptor(methodParam, this.required);
						descriptors[i] = desc;
						Object arg = beanFactory.resolveDependency(desc, beanName, autowiredBeanNames, typeConverter);
						arguments[i] = arg;
						...
						setup this.cachedMethodArguments
					}
					if (arguments != null) {
						ReflectionUtils.makeAccessible(method);
						method.invoke(bean, arguments);
					}
				}
			}
		}
	}
	
	isAutowireCandidate(String beanName, DependencyDescriptor descriptor, AutowireCandidateResolver resolver)(DefaultListableBeanFactory){
		if (containsBeanDefinition(beanDefinitionName)) {
			return isAutowireCandidate(beanName, getMergedLocalBeanDefinition(beanDefinitionName), descriptor, resolver);
			{
				resolveBeanClass(mbd, beanDefinitionName);
				if (mbd.isFactoryMethodUnique) {//for @Bean not static method
					boolean resolve = (mbd.resolvedConstructorOrFactoryMethod == null);
					if (resolve) {
						new ConstructorResolver(this).resolveFactoryMethodIfPossible(mbd);{
							too complex ???
						}
					}
				}
				return resolver.isAutowireCandidate(new BeanDefinitionHolder(mbd, beanName, getAliases(beanDefinitionName)), descriptor);{
					boolean match = super.isAutowireCandidate(bdHolder, descriptor){
						if (!bdHolder.getBeanDefinition().isAutowireCandidate()) {
							// if explicitly false, do not proceed with any other checks
							return false;
						}
						//checkGenericTypeMatch ??? if No generic type, it will return true
						return (descriptor == null || checkGenericTypeMatch(bdHolder, descriptor));
					}
					if (match && descriptor != null) {
						//descriptor.getAnnotations() returns field's annotations or methodParameter's annotations
						//getParameterAnnotations of reflect
						match = checkQualifiers(bdHolder, descriptor.getAnnotations());
						if (match) {
							MethodParameter methodParam = descriptor.getMethodParameter();
							//Why we do this (Qualifier can be used on method and parameters)
							if (methodParam != null) {
								Method method = methodParam.getMethod();
								//Constructor parameters or void method
								if (method == null || void.class.equals(method.getReturnType())) {
									//methodParam.getMethodAnnotations() returns all annotations of method/constructor
									//getAnnotations of reflect
									match = checkQualifiers(bdHolder, methodParam.getMethodAnnotations());
								}
							}
						}
					}
					return match;
				}
			}
		}
		else if (containsSingleton(beanName)) {
			//the same function as above
			return isAutowireCandidate(beanName, new RootBeanDefinition(getType(beanName)), descriptor, resolver);
		}
		else if (getParentBeanFactory() instanceof DefaultListableBeanFactory) {
			// No bean definition found in this factory -> delegate to parent.
			return ((DefaultListableBeanFactory) getParentBeanFactory()).isAutowireCandidate(beanName, descriptor, resolver);
		}
		else if (getParentBeanFactory() instanceof ConfigurableListableBeanFactory) {
			// If no DefaultListableBeanFactory, can't pass the resolver along.
			return ((ConfigurableListableBeanFactory) getParentBeanFactory()).isAutowireCandidate(beanName, descriptor);
		}
		else {
			return true;
		}
	}
	
	//do not register the qualifier to resolver but still use it, a bug??? consider it as by type, actually it does not take effect
	checkQualifiers(BeanDefinitionHolder bdHolder, Annotation[] annotationsToSearch){
		for (Annotation annotation : annotationsToSearch) {
			Class<? extends Annotation> type = annotation.annotationType();
			boolean checkMeta = true; //check annotation type's annotation
			boolean fallbackToMeta = false; //accept fallback match
			if type in this.qualifierTypes {
				if (!checkQualifier(bdHolder, annotation, typeConverter)) {
					fallbackToMeta = true;
				}
				else {
					checkMeta = false;
				}
			}
			if (checkMeta) {
				for (Annotation metaAnn : type.getAnnotations()) {
					Class<? extends Annotation> metaType = metaAnn.annotationType();
					if metaType in this.qualifierTypes {
						foundMeta = true;
						// Only accept fallback match if @Qualifier annotation has a value
						// and then check qualifier match
						if ((fallbackToMeta && StringUtils.isEmpty(AnnotationUtils.getValue(metaAnn))) ||
								!checkQualifier(bdHolder, metaAnn, typeConverter)) {
							return false;
						}
					}
				}
				if (fallbackToMeta && !foundMeta) {
					return false;
				}
			}
		}
		return true;
	}
	checkQualifier(BeanDefinitionHolder bdHolder, Annotation annotation, TypeConverter typeConverter) (QualifierAnnotationAutowireCandidateResolver){
		//just <qualifier> will register qualifier to bd
		AutowireCandidateQualifier qualifier = bd.getQualifier(type.getName());
		if (qualifier == null) {
			qualifier = bd.getQualifier(ClassUtils.getShortName(type));
		}
		//@Qualifier has no qualifier
		if (qualifier == null) {
			// First, check annotation on factory method, if applicable
			@Qualifier with @Bean
			// Look for matching annotation on the target class
			@Qualifier with @Component
			//just use equals, much easier than the xml qualifier
			if (targetAnnotation != null && targetAnnotation.equals(annotation)) {
				return true;
			}
		}
		//use xml qualifier, much more trivial
		Map<String, Object> attributes = AnnotationUtils.getAnnotationAttributes(annotation);
		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();
			Object expectedValue = entry.getValue();
			Object actualValue = null;
			if (qualifier != null) {
				actualValue = qualifier.getAttribute(attributeName);
			}
			
			Fall back to deal with actualValue ???
			
			if (actualValue != null) {
				actualValue = typeConverter.convertIfNecessary(actualValue, expectedValue.getClass());
			}
			if (!expectedValue.equals(actualValue)) {
				return false;
			}
		}
		return true;
	}
	
ConfigurationClassPostProcessor: has ConfigurationClassParser and ConfigurationClassBeanDefinitionReader
	postProcessBeanDefinitionRegistry{
		registry.registerBeanDefinition(IMPORT_AWARE_PROCESSOR_BEAN_NAME, iabpp);
		registry.registerBeanDefinition(ENHANCED_CONFIGURATION_PROCESSOR_BEAN_NAME, ecbpp);
		int registryId = System.identityHashCode(registry);
		this.registriesPostProcessed.add(registryId);
		processConfigBeanDefinitions(registry);
	}
	
	postProcessBeanFactory{
		int factoryId = System.identityHashCode(beanFactory);
		if (!this.registriesPostProcessed.contains(factoryId))
			processConfigBeanDefinitions((BeanDefinitionRegistry) beanFactory);
		enhanceConfigurationClasses(beanFactory);
	}
	
	processConfigBeanDefinitions{
		find Configuration class (full/lite)
		
		...
		
		// Read the model and create bean definitions based on its content
		...
		if (metadata.isStatic()) {
			// static @Bean method
			beanDef.setBeanClassName(configClass.getMetadata().getClassName());
			beanDef.setFactoryMethodName(metadata.getMethodName());
		}
		else {
			// instance @Bean method
			beanDef.setFactoryBeanName(configClass.getBeanName());
			beanDef.setUniqueFactoryMethodName(metadata.getMethodName());
			//call setFactoryMethodName() and set isFactoryMethodUnique to true
		}
		beanDef.setAutowireMode(RootBeanDefinition.AUTOWIRE_CONSTRUCTOR);
		register beanDef(ConfigurationClassBeanDefinition) for every bean within Configuration class
		...

		// Register the ImportRegistry as a bean in order to support ImportAware @Configuration classes
		if (singletonRegistry != null) {
			if (!singletonRegistry.containsSingleton(IMPORT_REGISTRY_BEAN_NAME)) {
				singletonRegistry.registerSingleton(IMPORT_REGISTRY_BEAN_NAME, parser.getImportRegistry());
			}
		}
	}
	
	enhanceConfigurationClasses{
		find Configuration class (full) 
		
		ConfigurationClassEnhancer enhancer = new ConfigurationClassEnhancer();
		
		//enhancedClass has no annotations on the overrided methods which are declared within itself !!!
		enhancedClass = enhancer.enhance(){
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(superclass);
			enhancer.setInterfaces(new Class<?>[] {EnhancedConfiguration.class});
			enhancer.setUseFactory(false);
			enhancer.setCallbackFilter(CALLBACK_FILTER);
			enhancer.setCallbackTypes(CALLBACK_FILTER.getCallbackTypes());
			enhancer.setStrategy(new DefaultGeneratorStrategy() { 
				@Override
				protected ClassGenerator transform(ClassGenerator cg) throws Exception {
					ClassEmitterTransformer transformer = new ClassEmitterTransformer() {
						@Override
						public void end_class() {
							declare_field(Constants.ACC_PUBLIC, BEAN_FACTORY_FIELD,
									Type.getType(BeanFactory.class), null);
							super.end_class();
						}
					};
					return new TransformingClassGenerator(cg, transformer);
				}
			});
			Class<?> subclass = enhancer.createClass();
			// Registering callbacks statically (as opposed to thread-local)
			// is critical for usage in an OSGi environment (SPR-5932)...
			Enhancer.registerStaticCallbacks(subclass, CALLBACKS);
			return subclass;
		}
		
		replace original configuration class with enhancedClass
	}
	
	private static final Callback[] CALLBACKS = new Callback[] {
			new BeanMethodInterceptor(),
			new DisposableBeanMethodInterceptor(),
			new BeanFactoryAwareMethodInterceptor(),
			NoOp.INSTANCE
	};

