package com.javassist;

import javassist.ClassPool;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/11/2
 * Time: 10:21
 * Desc:
 */
public class testImportPackage {
    public static void main( String args[] ) throws Exception{
        ClassPool pool = ClassPool.getDefault();
        pool.importPackage("javax.annotation");


    }
}
