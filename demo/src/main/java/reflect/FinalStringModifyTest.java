package reflect;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/4/1
 * Time: 11:00
 * Desc:
 */
public class FinalStringModifyTest {

    private final String test = "test";
            // new StringBuilder("test").toString();

    public String getTest() {
        return test; //compile to return "test";
    }

    public static void main(String[] args) throws Exception{
        FinalStringModifyTest test = new FinalStringModifyTest();
        System.out.println(test.getTest());//test
        Class<FinalStringModifyTest> klass = FinalStringModifyTest.class;
        Field field = klass.getDeclaredField("test");
        field.setAccessible(true);
        field.set(test, "new");

        System.out.println(test.getTest());//test
        System.out.println(field.get(test));//new
    }
}
