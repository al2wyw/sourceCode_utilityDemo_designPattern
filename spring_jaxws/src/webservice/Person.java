package webservice;
import java.io.Serializable;
public class Person implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	private double salary;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public Person(String name, String id, double salary) {
		super();
		this.name = name;
		this.id = id;
		this.salary = salary;
	}
	public Person() {
		
	}
	@Override
	public int hashCode() {
		return name.hashCode()+id.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Person){
			Person p=(Person)obj;
			return name.equals(p.getName())&&
					id.equals(p.getId())&&
					salary == p.getSalary();
		}
		System.out.println("test");
		return false;
	}
}
