package guava;

import com.google.common.base.Preconditions;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/11/29
 * Time: 16:41
 * Desc:
 */
public class testPrecondition {
    public static void main(String[] args) throws Exception{
        String test = null;
        Preconditions.checkArgument(test == null,"test");
    }
}
