package reflect.name;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/10/17
 * Time: 20:56
 * Desc:
 */
public class GetName {

    public static class InnerName {
        public InnerName(){}
        private int x;
        private void x(){}
    }

    public class MemberInnerName{
        private int x;

        public int getX() {
            return x;
        }
    }

    private class PriMemberInnerName{
        private int x;

        public int getX() {
            return x;
        }
    }

    public void test(String[] args){
        InnerName innerName = new InnerName();
        innerName.x = 10;
        Arrays.asList(innerName).stream().forEach(innerName1 -> System.out.println(innerName.x));

        MemberInnerName memberInnerName = new MemberInnerName();
        memberInnerName.x = 199;

        PriMemberInnerName priMemberInnerName = new PriMemberInnerName();
        priMemberInnerName.x = 34;
    }
}
