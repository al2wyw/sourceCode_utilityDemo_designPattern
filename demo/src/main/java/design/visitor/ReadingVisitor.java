package design.visitor;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/9/25
 * Time: 11:51
 * Desc:
 */
public class ReadingVisitor implements Visitor{
    public void visitDoctor(Doctor doctor) {
        visitHuman(doctor);
        System.out.println("Skill: "+doctor.getSkill());
    }

    public void visitNurse(Nurse nurse) {
        visitHuman(nurse);
        System.out.println("Level: "+nurse.getLevel());
    }

    public void visitHuman(Human v) {
        System.out.println(v.getName());
    }
}
