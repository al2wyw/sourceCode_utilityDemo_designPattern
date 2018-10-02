package reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class test_raw {

	public List<bean> getBeans() {
		return beans;
	}

	public void setBeans(List<bean> beans) {
		this.beans = beans;
	}

	private List<bean> beans;
	
	public static void main(String[] args) throws Exception {
		test_raw t = new test_raw();
		List<bean> b = new ArrayList<bean>();
		List<?> b_ = b;
		if (b instanceof List<?>)
			System.out.println("yes");
		
		PersonDAO dao = new PersonDAO();
        Person p = dao.createInstance();
        System.out.println(p.toString());

        NewPersonDAO newPersonDAO = new NewPersonDAO();
        p = newPersonDAO.createInstance();
        System.out.println(p.toString());
	}

}

class Person {
    public String toString() {
        return "I'm a person";
    }
}

class NewPersonDAO extends PersonDAO{

}
class PersonDAO extends TypeExtractor<Person> {
}

class TypeExtractor<T> {
    public T createInstance() throws Exception {
        Class<T> clazz = getBeanClass();
        return clazz.newInstance();
    }

    @SuppressWarnings("unchecked")
    public Class<T> getBeanClass() {
        Type type = getClass().getGenericSuperclass();
        while(type != null) {
            if (type instanceof ParameterizedType) {
                return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
            }
            type = ((Class) type).getGenericSuperclass();
            //根据源码，getGenericSuperclass只会返回Class和ParameterizedType
            //sun.reflect.generics.visitor.Reifier.visitClassTypeSignature 会初始化 resultType
        }
        return null;
    }
}