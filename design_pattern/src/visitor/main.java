package visitor;

public class main {

	public static void main(String[] args) {
		VisitorV1 v = new VisitorV1();
		SmallTester t = new SmallTester();
		t.accept(v);
	}

}
