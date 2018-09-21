package classloader;

import java.lang.Integer;import java.lang.System;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/5/15
 * Time: 15:41
 * Desc:
 */
public class Test {

    public static final int C = 1;//不会clinit
    public static final Integer A = 1;//会clinit
    public static final int B = new Integer(1);//会clinit

    static{
        System.out.println("Test clinit");
    }
}
