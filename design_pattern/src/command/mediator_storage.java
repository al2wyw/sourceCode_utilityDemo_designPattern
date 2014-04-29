package command;

public class mediator_storage implements mediator{
	private Receiver r;
	private CareTaker c;
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
	public void undo(){
		
	}
	public Receiver getR() {
		return r;
	}
	public void setR(Receiver r) {
		this.r = r;
	}
	public CareTaker getC() {
		return c;
	}
	public void setC(CareTaker c) {
		this.c = c;
	}
	
}
