package demo;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/4/29
 * Time: 20:47
 * Desc: 方法重写(动态派发): 根据调用对象的实际类型来进行派发，编译期不可知，运行期可变
 */
public class DynamicDispatch {

    private static abstract class Human{
        public abstract void work();
        public static void test(){
            System.out.println("human test");
        }
    }

    private static class Man extends Human{
        @Override
        public void work() {
            System.out.println("Man work");
        }

        public static void test(){
            System.out.println("Man test");
        }
    }

    private static class Woman extends Human{
        @Override
        public void work() {
            System.out.println("Woman work");
        }

        public static void test(){
            System.out.println("Woman test");
        }
    }

    public static void main(String args[]) {
        Human man = new Man();
        Human woman = new Woman();
        //先进行静态派发再动态派发
        man.work();// INVOKEVIRTUAL demo/DynamicDispatch$Human.work ()V
        woman.work();// INVOKEVIRTUAL demo/DynamicDispatch$Human.work ()V
        man.test();// 静态派发到父类了
    }
}
