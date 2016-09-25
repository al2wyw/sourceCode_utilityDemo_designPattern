package design.visitor;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/9/25
 * Time: 11:48
 * Desc:
 */
public interface Visitor { // can only has one visit method, use reflect to get the correct visit method from sub class
    void visitDoctor(Doctor doctor);
    void visitNurse(Nurse nurse);
    void visitHuman(Human human);
}
