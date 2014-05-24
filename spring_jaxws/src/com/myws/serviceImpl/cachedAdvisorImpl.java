package com.myws.serviceImpl;

import com.myws.service.cachedAdvisor;
import org.aspectj.lang.ProceedingJoinPoint;
import net.spy.memcached.MemcachedClient;
import java.util.concurrent.Future;
public class cachedAdvisorImpl implements cachedAdvisor {
	private MemcachedClient memcachedClient;
	
	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}
	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}
	@Override
	public Object cachedHandler(ProceedingJoinPoint point,String key) throws Throwable {
		// TODO Auto-generated method stub
		if(key!=null && !key.trim().equals("")){
			System.out.println("key is "+ key);
			Object o=memcachedClient.get(key);
			if(o==null){
				o=point.proceed();
				if(o!=null){
					System.out.println("to set cached with key "+key);
					Future<Boolean> b = null;
					b = memcachedClient.set(key,30, o);
					while(!b.isDone());
					if(b!=null&& b.get())
						System.out.println(key+ " is cached");
				}
			}
			return o;
		}
		return null;
	}

}
