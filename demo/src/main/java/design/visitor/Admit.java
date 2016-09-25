package design.visitor;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/9/25
 * Time: 12:58
 * Desc:
 */
public class Admit extends Human{
    private String test;

    public void accept(Visitor v) {
        Visitorext visitorext = (Visitorext) v;
        visitorext.visitAdmit(this);
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
