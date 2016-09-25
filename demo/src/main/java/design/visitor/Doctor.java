package design.visitor;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/9/25
 * Time: 12:36
 * Desc:
 */
public class Doctor extends Human {
    private String skill;

    public void accept(Visitor v) {
        v.visitDoctor(this);
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
