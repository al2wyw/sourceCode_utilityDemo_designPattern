package com.cglib;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.asm.Type;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.core.ClassGenerator;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.Constants;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.core.DefaultNamingPolicy;
import net.sf.cglib.core.EmitUtils;
import net.sf.cglib.core.Predicate;
import net.sf.cglib.core.ReflectUtils;
import net.sf.cglib.core.Signature;
import net.sf.cglib.transform.ClassEmitterTransformer;
import net.sf.cglib.transform.TransformingClassGenerator;

public class testBeanGenerator {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		
		File f = new File(test.class.getResource("/").getFile());
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, f.getPath());
        
        BeanGenerator bg = new BeanGenerator();
        bg.addProperty("name", String.class);
        bg.addProperty("sex", String.class);
        bg.addProperty("salary", double.class);
        bg.setNamingPolicy(new DefaultNamingPolicy(){
        	public String getClassName(String prefix, String source, Object key, Predicate names) {
        		return "com.cglib.GeneratedBean";
        	}
        });
        bg.setStrategy(new DefaultGeneratorStrategy(){
        	@Override
			protected ClassGenerator transform(ClassGenerator cg) throws Exception {
				ClassEmitterTransformer transformer = new ClassEmitterTransformer() {
					@Override
					public void end_class() {
						declare_field(Constants.ACC_PUBLIC, "special",
								Type.getType(String.class), "my special field");//the value does not work!
						
						CodeEmitter e = begin_method(Constants.ACC_PUBLIC, 
								new Signature("myMethod",Type.VOID_TYPE,new Type[]{Type.BOOLEAN_TYPE,Constants.TYPE_BOOLEAN}), //boolean, Boolean
								null);
						e.load_this();
					    e.return_value();//must have   !!!
				        e.end_method();
				        
				        //method signature just contains name and parameter type list, no return type
				        CodeEmitter t = begin_method(Constants.ACC_PUBLIC, 
								new Signature("test_to_load",Type.VOID_TYPE,new Type[]{Type.BOOLEAN_TYPE}),
								null);
				        try {
				        	//just call getDeclaredMethod("to_load", new Class[]{Boolean.TYPE}) of test_to_load.class, no use
							EmitUtils.load_method(t, ReflectUtils.getMethodInfo(test_to_load.class.getMethod("to_load", new Class<?>[]{boolean.class})));
						} catch (NoSuchMethodException | SecurityException e1) {
					
							e1.printStackTrace();
						}
				        t.return_value();
				        t.end_method();
				        
						super.end_class();
					}
				};
				return new TransformingClassGenerator(cg, transformer);
			}
        });
        Object o = bg.create();
        Method m = o.getClass().getMethod("setName", String.class);
        m.invoke(o, "Peter");
        m = o.getClass().getMethod("getName",null);
        String s = (String)m.invoke(o, null);
        System.out.println(s);
        String special = (String)o.getClass().getField("special").get(o);
        System.out.println(special);
	}

}

class test_to_load{
	public void to_load(boolean t){
		System.out.println(t);
	}
}