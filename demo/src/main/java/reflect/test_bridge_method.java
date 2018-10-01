package reflect;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 *  volatile方法并不存在，其实就是bridge方法，是老jdk的一个bug，一个bridge方法在反编译后会显示成volatile
 *
 *  */
public class test_bridge_method {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*D test = new D();
		Object t = test.id(new Object());
		System.out.println(t);*/
		Class<D> k = D.class;
		Method[] arr = k.getDeclaredMethods();//会返回bridge方法
		System.out.println(arr.length);//it is 2
		Arrays.asList(arr).forEach(ar -> System.out.println(ar.getName()));
		arr = k.getMethods();//会返回bridge方法
		System.out.println(arr.length);
		Arrays.asList(arr).forEach(ar -> System.out.println(ar.getName()));
	}

}

class B<T>{
	private T object;

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
}

abstract class C<T> {
    public abstract T id(T x); // -> Object id(Object x)
}

class D extends C<String> {
	public String id(String x) {
		return x;
	}
	//编译时根据模板C的方法多生成一个synthetic bridge 方法:
	//Object id(Object x){ return id((String)x);}
	//bridge方法的实现都是这个样子，cast后调用非bridge方法
}
