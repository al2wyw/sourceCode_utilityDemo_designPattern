package demo;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/4/29
 * Time: 20:11
 * Desc: 方法重载(静态派发): 根据入参的静态类型(外观类型)和个数来进行派发，编译期可知，运行期不变
 *                           派发的逻辑主要是找到最"接近"入参的静态类型的方法，重载过多的话会比较复杂
 */
public class StaticDispatch {

    private static class Human{

    }

    private static class Man extends Human{

    }

    private static class Woman extends Human{

    }

    public void work(Human h){
        System.out.println("human work");
    }

    public void work(Man h){
        System.out.println("Man work");
    }

    public void work(Woman h){
        System.out.println("Woman work");
    }

    public static void main(String args[]) {
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch dispatch = new StaticDispatch();
        dispatch.work(man); //INVOKEVIRTUAL demo/StaticDispatch.work (Ldemo/StaticDispatch$Human;)V 编译后已经把方法决定了
        dispatch.work(woman);
    }
}
