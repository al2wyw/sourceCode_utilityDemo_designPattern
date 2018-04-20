package designPattern.visitor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractVisitor implements Visitor {

	@Override
	public void visit(Tester t){
		Class<?> klass = t.getClass();
		String name = klass.getName();
		name = name.split("\\.")[1];
		String method = "visit"+name;
		try {
			Class<?>[] args = new Class<?>[]{Tester.class};
			Method visit = this.getClass().getMethod(method, args);
			if(visit!=null){
				try {
					Object[] objs = new Object[]{t};
					visit.invoke(this, objs);
				} catch (IllegalAccessException e){
					e.printStackTrace();
				}
				catch (IllegalArgumentException e){
					e.printStackTrace();
				}
				catch(InvocationTargetException e) {

					e.printStackTrace();
				}
			}
		} catch (NoSuchMethodException e) {
			
			revisit(t);
		} catch ( SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

	
}
