package reflect;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Date;

public class test_propert_descriptor {

	public static void main(String[] args) throws IntrospectionException {
		// TODO Auto-generated method stub
		PropertyDescriptor pd = new PropertyDescriptor("time",bean.class);
		Class<?> idType = pd.getPropertyType();
		System.out.println(idType.getName());
	}

}

class bean{
	private int id;
	private String name;
	private double salary;
	private Date time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	//forge the property type
	public void setTime(int time){
		
	}
	public int getTime() {
		return 3;
	}
	
}