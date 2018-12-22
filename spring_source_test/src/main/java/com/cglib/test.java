package com.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by apple on 22/04/2015.
 */
public class test {
    public static void main(String[] args) throws Exception {
		CglibUtils.setupOutPutDir();
        Enhancer enhancer = new Enhancer();
        //setSuperclass or setInterfaces will generate subclass codes file, extends or implements
        //if neither of them is called, no codes file generated but class object(extends Object)
        //enhancer.setSuperclass(myDot.class);
        enhancer.setSuperclass(myTest.class);
        //enhancer.setInterfaces(new Class<?>[] {myDot.class});
        enhancer.setUseFactory(false);
        enhancer.setCallbacks(new Callback[] {NoOp.INSTANCE
        });
        Object proxy = enhancer.create();
        Class<?> sup = proxy.getClass().getSuperclass();
        System.out.println(sup.getName());
        System.out.println(((myTest)proxy).getMy());
    }
}

class myTest{
	@Autowired
	private int pro;
	
	public String getMy(){
		return "myTest";
	}
	
	private String name;
	protected int id;
	public double salary;
	
	private String getall(){
		return name.toLowerCase();
	}
	
	public double show(){
		if (salary > 100.34)
			return salary;
		else
			return 43205.34;
	}
	
	public myTest(){
		System.out.println("default");
	}
	
	public myTest(double salary){
		this.salary = salary;
		System.out.println("default");
	}
}

interface myDot {
    void doit();
}
