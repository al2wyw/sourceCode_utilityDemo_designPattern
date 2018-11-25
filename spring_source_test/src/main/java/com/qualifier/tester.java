package com.qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *  @Qualifier 不支持 placeholder
* */
public class tester {
	@Autowired
	@customQ("test_custom_qualifier")
	private Iteste ssss;
	
	@Autowired
	@anotherCustomQ("test_another_custom_qualifier")//anotherCustomQ不配置在CustomAutowireConfigurer里等于注解无效，最终变成autowire by type
	private Iteste tttt;

	@Autowired
	@customQ("testeeeeQual")
	private Iteste qqqq;
	
	public void call(){
		ssss.test();
		tttt.test();
		qqqq.test();
	}
	
	
}
