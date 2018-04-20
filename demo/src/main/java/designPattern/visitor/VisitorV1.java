package designPattern.visitor;

public class VisitorV1 extends AbstractVisitor {
	public void visitSmallTester(Tester t){
		System.out.println("small tester");
		SmallTester test = (SmallTester)t;
		test.setId(100);
		test.setName("peter");
	}
	public void visitBigTester(Tester t){
		System.out.println("big tester");
		BigTester test = (BigTester)t;
		test.setCanTest(true);
	}
	public void revisit(Tester t){
		System.out.println("tester");
	}
}
