package com.aop;

public class systemArrayCopy {

	public static void main(String[] args) {
		person p1 = new person();
		p1.setName("peter");
		person p2 = new person();
		p2.setName("anne");
		person p3 = new person();
		p3.setName("ken");
		person[] array=new person[]{p1,p2,p3};
		person[] array1=new person[3];
		System.arraycopy(array, 0, array1, 0, 3);//deep copy,but just copy the reference from one array to another array. Arrays.copyof will call arraycopy
		array1[0].setName("smith");
		for(person s:array){
			System.out.println(s.toString());
		}
	}

}

class person{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String toString(){
		return name;
	}
}