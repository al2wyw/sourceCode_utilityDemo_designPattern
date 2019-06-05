package com.cglib;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/6/3
 * Time: 21:04
 * Desc: const pool: integer long double float utf8 ги5 basic type)
 */
public class AutoboxTest {

    private static final double CONS_DOU = 123.34;

    private static final int CONS_INT = 123;

    private static final long CONS_LON = 353l;

    private static final float CONS_FLO = 34.54f;

    private static final boolean CONS_BL = true;

    public static void main(String[] args) throws Exception{
        Object[] objects = new Object[4];
        objects[0] = 7;//Integer.valueOf
        objects[1] = "test";
        objects[2] = Integer.valueOf("534333");
        objects[3] = false; //iconst_0 -> Boolean.valueOf

        for(int i = 0; i< 4; i++){
            System.out.println(objects[i].getClass());
        }
        int j = (int)objects[2];//checkcast <java/lang/Integer> -> invokevirtual <java/lang/Integer.intValue>
        System.out.println(j);
        int h = (Integer)objects[2];//checkcast <java/lang/Integer> -> invokevirtual <java/lang/Integer.intValue>
        System.out.println(h);
    }
}
