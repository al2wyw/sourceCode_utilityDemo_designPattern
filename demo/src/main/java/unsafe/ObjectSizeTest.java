package unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/4/5
 * Time: 11:26
 * Desc: -javaagent:agent.jar
 */
public class ObjectSizeTest {

    private static Unsafe unsafe;

    static{
        try {
            Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeInstance.setAccessible(true);
            unsafe = (Unsafe) theUnsafeInstance.get(Unsafe.class);
            if (unsafe == null) {
                System.out.println("can not get unsafe");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static long getObjectSize(Object objectToSize){
        return InstrumentationSteal.instrumentation.getObjectSize(objectToSize);
    }

    public static void main(final String[] args) throws Exception {

        System.out.println(unsafe.objectFieldOffset(TestClass.class.getDeclaredField("i")));
        System.out.println(unsafe.objectFieldOffset(TestClass.class.getDeclaredField("h")));
        System.out.println(unsafe.objectFieldOffset(TestClass.class.getDeclaredField("n")));

        TestClass testClass = new TestClass();
        System.out.println(getObjectSize(testClass));

        System.out.println(unsafe.objectFieldOffset(TestClassExt.class.getDeclaredField("j")));
        System.out.println(unsafe.objectFieldOffset(TestClassExt2.class.getDeclaredField("m")));
        System.out.println();
        System.out.println(unsafe.objectFieldOffset(Togeth.class.getDeclaredField("c")));
        System.out.println(unsafe.objectFieldOffset(Togeth.class.getDeclaredField("i1")));
        System.out.println(unsafe.objectFieldOffset(Togeth.class.getDeclaredField("i2")));
        System.out.println(unsafe.objectFieldOffset(Togeth.class.getDeclaredField("i3")));
        System.out.println(unsafe.objectFieldOffset(Togeth.class.getDeclaredField("count")));
        System.out.println(unsafe.objectFieldOffset(Togeth.class.getDeclaredField("test")));
        System.out.println(unsafe.objectFieldOffset(Togeth.class.getDeclaredField("test1")));
        System.out.println(unsafe.objectFieldOffset(Test.class.getDeclaredField("count")));

        Togeth togeth = new Togeth();
        System.out.println(getObjectSize(togeth));
    }
}
class TestClassExt2 extends  TestClassExt{
    private byte m;
}
class TestClassExt extends TestClass{
    private byte j;
}
class TestClass{
    private byte i;
    private long h;
    private char n;
}
class Togeth{
    private byte i1;
    private byte i2;
    private byte i3;
    private int h;
    private char c;
    private long count;
    private TestClass test;
    private TestClassExt test1;
}
class Test extends Togeth{
    private long count;
}
/**
 * 对象头:
 * +------------------+------------------+--------------------------------+---------------+---------------+
 * |    mark word     |   klass pointer  |  array size (optional,4bytes) |     data      |    padding    |
 * +------------------+------------------+------------------------------+---------------+---------------+
 *  基本类型在32,64位jvm里占用空间是一样的,只用引用的大小不一样, 开启"-XX:+UseCompressedOops"后引用大小一样(小于32g内存默认开启)
 *  mark word : 4 bytes(32), 8 bytes(64)
 *
 *  non-static inner class has an extra pointer to outer class!
 *  对象整体8 byte对齐
 *  (https://blog.csdn.net/liu251/article/details/50190247)
 *  父类实例成员变量结束之后，按4 byte补齐padding; 如果子类有 long, 而父类没有 8 byte对齐会把子类的小于 8 byte的成员先排列(如果对象头是12byte,也会先用成员补齐头部)
 *  rearrange order:  int 4 byte对齐，long 8 byte对齐, 也就是说int的起始地址必须是4 byte的整数倍(前一个字段补padding)，long的起始地址必须是8 byte的整数倍
 *  doubles and longs
 *  ints and floats
 *  shorts and chars
 *  booleans and bytes
 *  references

 * 对象大小分为:
 * 自身的大小(Shadow heap size)
 * 所引用的对象的大小(Retained heap size),递归算得
 * */