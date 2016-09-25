package design.visitor;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/9/25
 * Time: 11:49
 * Desc:
 */
public class Human implements Visitable {

    private String name;

    public void accept(Visitor v) {
        v.visitHuman(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
