package design.visitor;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/9/25
 * Time: 11:48
 * Desc:
 */
public interface Visitable {
    void accept(Visitor v);
}
