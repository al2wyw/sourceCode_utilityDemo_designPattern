package com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/3/28
 * Time: 18:09
 * Desc:
 */
public class DateUtils {

    public static final String DATE_TIME_MS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String format(Date date, String format){
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        return format1.format(date);
    }
}
