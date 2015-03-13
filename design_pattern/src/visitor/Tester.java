package visitor;

public class Tester {

	public void accept(Visitor v){
		v.visit(this);
	}
}
