package com.objenesis;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/8/2
 * Time: 18:17
 * Desc:
 */
public class ObjenesisTest {

    public static void main( String args[] ) throws Exception{
        //sun hotspot vm运行时会使用ReflectionFactory.newConstructorForSerialization, 这是java在反序列化时绕开原类的构造函数时调用的接口
        //强依赖运行时的vm: StdInstantiatorStrategy.newInstantiatorOf
        Objenesis objenesis = new ObjenesisStd(true);
        Test test = (Test)objenesis.newInstance(Test.class);
        test.show();
    }

    public static class Test{

        private int i;

        public Test(int i){
            this.i = i;
        }

        public void show(){
            System.out.println("show " + i);
        }
    }
}
