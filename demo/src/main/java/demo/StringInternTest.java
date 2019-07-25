package demo;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/10/26
 * Time: 15:50
 * Desc: https://www.jianshu.com/p/c14364f72b7e
 *  1. 字符串常量池已经挪到heap里面
 *  2. ldc相当于对字符串进行intern操作
 *  3. StringTableSize可以控制字符串常量池的table大小，避免哈希冲突
 */
public class StringInternTest {

    public static void main(String[] args) {
        String s1 = new StringBuilder().append("jav").append("va").toString();
        //1.6 返回false是由于常量池在perm区，s1(堆区)的地址和intern(perm区)的地址永远不可能相同
        System.out.println(s1 == s1.intern()); //true，"javva"字符串不在常量池，intern把"javva"存储到常量池里面并返回地址，并且常量池在堆里面，所有地址相同
        String s2 = new StringBuilder().append("ja").append("va").toString();
        System.out.println(s2 == s2.intern()); //false，"java"字符串已经在常量池了，intern返回"java"在常量池里面的地址

        String s3 = new StringBuilder().append("print").append("ln").toString();
        System.out.println(s3 == s3.intern());//true, println 虽然出现在class文件的常量池里，但是运行时并不在字符串常量池里

        String s4 = ConstantTest.my;//compiled to: ldc "test", no ConstantTest relevance

        test();
    }

    public static void test(){
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program";
        String s4 = "ming";
        String s5 = "Program" + "ming"; // complied to LDC "Programming" !!!
        String s6 = s3 + s4; // complied to StringBuilder and then StringBuilder.toString
        System.out.println(s1 == s2);
        System.out.println(s1 == s5);
        System.out.println(s1 == s6);
        System.out.println(s1 == s6.intern());
        System.out.println(s2 == s2.intern());
    }

    private static class ConstantTest {
        private final static String my = "test";
    }
}
