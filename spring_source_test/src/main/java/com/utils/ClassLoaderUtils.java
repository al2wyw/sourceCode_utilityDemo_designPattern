package com.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/5/30
 * Time: 19:08
 * Desc:
 */
public class ClassLoaderUtils {

    private static Method defineClass0;

    static
    {
        try
        {
            Method field = ClassLoader.class.getDeclaredMethod("defineClass0",
                    String.class,byte[].class, int.class, int.class, ProtectionDomain.class);
            field.setAccessible(true);
            defineClass0 = field;
        } catch (Exception e){
            LoggerUtils.getLogger().error("",e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T defineInstance(ClassLoader cl, String name, byte[] newContent){
        Class klass = defineClass(cl,name,newContent);
        try {
            return (T) klass.newInstance();
        }catch (Exception e){
            LoggerUtils.getLogger().error("",e);
            return null;
        }
    }

    public static Class defineClass(ClassLoader cl, String name, byte[] newContent){
        try {
            return (Class) defineClass0.invoke(cl, name, newContent, 0, newContent.length,
                    ClassLoaderUtils.class.getProtectionDomain());
        }catch (Exception e){
            LoggerUtils.getLogger().error("",e);
            return null;
        }
    }

    public static void saveClassFile(String fileName, byte[] newContent) throws IOException{
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        fileOutputStream.write(newContent);
        fileOutputStream.close();
    }
}
