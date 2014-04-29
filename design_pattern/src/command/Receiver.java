package command;

import java.util.Random;

public class Receiver {  
	private int value=100;
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
	public Mememto save(){
		Mememto m=new Mememto();
		m.setValue(getValue());
		return m;
	}
	public void restore(Mememto m){
		setValue(m.getValue());
	}
}  

