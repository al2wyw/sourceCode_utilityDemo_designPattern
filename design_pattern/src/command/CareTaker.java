package command;
import java.util.LinkedList;
public class CareTaker {
	private LinkedList<Mememto> histroy=new LinkedList<Mememto>();
	public Mememto retrieve(){
		if(!histroy.isEmpty()){
			return histroy.removeLast();
		}
		return null;
	}
	public void store(Mememto m){
		histroy.addLast(m);
	}
}
