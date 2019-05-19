package reflect.dispatcher;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/12/31
 * Time: 17:14
 * Desc:
 */
public class testReflect {

    public static void main(String[] args) throws Exception{
        Method method = testInterface.class.getDeclaredMethod("testMethod");//MethodAccessor invokeInterface
        Method method1 = testImpl.class.getDeclaredMethod("testMethod");//MethodAccessor invokeVirtual
        testImpl testImpl = new testImpl();
        method.invoke(testImpl);

        testInterface testInterface = testImpl;
        testInterface.testMethod(); //invokeInterface
        testImpl.testMethod(); //invokeVirtual

        /** test dispatcher **/
        testInterface testDisp = new testImpl();
        //如果有多个方法的继承关系一样近，方法调用方会编译报错
        testDisp.testDispatcher("String");//dispatch to Comparable param function, 找参数继承关系最近的方法来调用
        Method disp = testInterface.class.getDeclaredMethod("testDispatcher",Object.class);
        disp.invoke(testDisp, "String");//dispatch to object param function, 对参数会进行cast check

        /** test box dispatcher **/
        testDisp.testBoxDispatcher(1);//dispatch to int param function
        testDisp.testBoxDispatcher(Integer.valueOf(1));//dispatch to Integer param function
        Method boxDisp = testInterface.class.getDeclaredMethod("testBoxDispatcher",Integer.class);
        boxDisp.invoke(testDisp,1);//dispatch to Integer param function, 对参数会进行装箱拆箱处理

        testDisp.testAutoBoxDispatcher(1);
    }
}
