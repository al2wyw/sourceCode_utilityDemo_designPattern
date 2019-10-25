package com.objenesis;

import com.cglib.NoArgsContTest;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/8/2
 * Time: 18:17
 * Desc:
 *      newConstructorForSerialization 会生成 GeneratedSerializationConstructorAccessor1 extends SerializationConstructorAccessorImpl:
 *      new           #6                  // class java/lang/Number
 *      dup
 *      invokespecial #12                 // Method java/lang/Object."<init>":()V
 *      areturn
 *
 *      sun.reflect.SerializationConstructorAccessorImpl: new B; invokespecial A.init will throw Exception (verification fails)
 *      but this marker class was originally known to the VM and verification disabled for it and all subclasses
 *
 *      @see NoArgsContTest fail test
 */
public class ObjenesisTest {

    public static void main( String args[] ) throws Exception{
        //ObjenesisBase 才是骨干，ObjenesisStd只是为了写死InstantiatorStrategy字段的特种类
        Objenesis objenesis = new ObjenesisStd(true);
        //强依赖运行时的vm: StdInstantiatorStrategy.newInstantiatorOf
        Test test = (Test)objenesis.newInstance(Test.class);
        //sun hotspot vm运行时会使用 sun.reflect.ReflectionFactory.newConstructorForSerialization, 这是java在反序列化时绕开原类的构造函数时调用的接口

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
