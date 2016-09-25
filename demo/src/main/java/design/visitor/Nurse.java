package design.visitor;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/9/25
 * Time: 12:37
 * Desc:
 */
public class Nurse extends Human {
    private String level;

    public void accept(Visitor v) {
        v.visitNurse(this);
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
