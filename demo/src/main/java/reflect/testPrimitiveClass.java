package reflect;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/1/2
 * Time: 17:45
 * Desc:
 */
public class testPrimitiveClass {

    public static void main(String[] args) throws Exception{
        if(int.class == Integer.TYPE){
            System.out.println("int.class == Integer.TYPE");//true
        }
        if(int.class == Integer.class){
            System.out.println("int.class == Integer.class");//false
        }
        if(Integer.TYPE == Integer.class){
            System.out.println("Integer.TYPE == Integer.class");//false
        }
        try {
            int i = Integer.TYPE.newInstance();// no method int.<init>()
            System.out.println(i);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            Integer ii = Integer.class.newInstance();// no method java.lang.Integer.<init>()
            System.out.println(ii);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
