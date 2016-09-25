package design.visitor;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/9/25
 * Time: 13:02
 * Desc:
 */
public class ReadingVisitorext extends ReadingVisitor implements Visitorext{
    public void visitAdmit(Admit admit) {
        visitHuman(admit);
        System.out.println("admit test "+admit.getTest());
    }
}
