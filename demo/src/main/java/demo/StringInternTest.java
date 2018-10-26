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
    }
}
