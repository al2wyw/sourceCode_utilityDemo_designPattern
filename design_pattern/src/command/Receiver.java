package command;

import java.util.Random;

public class Receiver {  
	private int value=100;
	private CareTaker c=new CareTaker();
    public void action(){ 
        System.out.println("command received!");  
        Random ran=new Random();
        int i=ran.nextInt(100);
        setValue(getValue() + i);
    }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	public void save(){
		Mememto m=new Mememto();
		m.setValue(getValue());
		c.store(m);;
	}
	public void restore(){
		setValue(c.retrieve().getValue());
	}
}  

