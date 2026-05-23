package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintUtils {

    public static void print(Object message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(sdf.format(new Date()) + " [" + Thread.currentThread().getName() + "] " + message);
    }

}
