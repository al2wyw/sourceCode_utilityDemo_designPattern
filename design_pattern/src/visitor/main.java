package visitor;

public class main {

	public static void main(String[] args) {
		VisitorV1 v = new VisitorV1();
		SmallTester s = new SmallTester();
		BigTester b = new BigTester();
		MiddleTester m = new MiddleTester();
		s.accept(v);
		b.accept(v);
		m.accept(v);
	}

}
