package com.cglib;

import net.sf.cglib.core.DebuggingClassWriter;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2018/12/22
 * Time: 17:12
 * Desc:
 */
public class CglibUtils {

    public static void setupOutPutDir(){
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, CglibUtils.CGL_PATH);
    }

    public static final String CGL_PATH = System.getProperty("user.dir") + File.separator + "cglib_generate";
}