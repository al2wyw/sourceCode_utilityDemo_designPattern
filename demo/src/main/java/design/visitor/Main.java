package design.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/9/25
 * Time: 11:59
 * Desc:
 */
public class Main {
    public static void main(String[] args) throws Exception{
        Visitor v = new ReadingVisitorext();
        Human p = new Human();
        p.setName("peter");

        Doctor doc = new Doctor();
        doc.setName("doc");
        doc.setSkill("open heart");

        Nurse nurse = new Nurse();
        nurse.setName("nurse");
        nurse.setLevel("prof");

        Admit admit = new Admit();
        admit.setName("admit");
        admit.setTest("test for you");

        List<Human> humans = new ArrayList<Human>();
        humans.add(p);
        humans.add(doc);
        humans.add(nurse);
        humans.add(admit);
        for(Human human:humans){
            human.accept(v);
        }
    }
}
