package com.context;

import java.util.concurrent.ConcurrentHashMap;
import com.google.common.base.Strings;
import org.springframework.aop.framework.ProxyFactory;


public class ProxyFactoryContainer {
	private static ProxyFactoryContainer instance;
	private ConcurrentHashMap<String,ProxyFactory> container = new ConcurrentHashMap<String,ProxyFactory>();
	
	public void addProxyFactory(String name,ProxyFactory f){
		if(Strings.isNullOrEmpty(name))
			return;
		if(f == null)
			return;
		if(container.containsKey(name))
			return;
		container.put(name, f);
	}
	
	private ProxyFactoryContainer(){}
	
	public ProxyFactory getProxyFactory(String name){
		if(Strings.isNullOrEmpty(name))
			return null;
		return container.get(name);
	}
	
	public static ProxyFactoryContainer getInstance(){
		if(instance==null){
			synchronized(ProxyFactoryContainer.class){
				if(instance==null){
					instance = new ProxyFactoryContainer();
				}
			}
		}
		return instance;
	}
}
