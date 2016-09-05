package demo;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by johnny.ly on 2015/9/29.
 */
public class date_parse_test {

    private static SimpleDateFormat TimeFormat;

    static{
        TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");//标准时间，timestamp
        TimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) throws Exception{
        normal1();
        normal2();
        normal3();
        normal4();
        normal5();
        System.out.println(TimeZone.getDefault().toString());

        int base = 60*60*1000;
        System.out.println(Lists.newArrayList(TimeZone.getAvailableIDs(-8 * base)));
    }

    private static void normal1() throws Exception{
        SimpleDateFormat SuccessTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String test = "2015-08-28 08:00:00";
        Date time = SuccessTimeFormat.parse(test);
        if(time!=null) {
            System.out.println(SuccessTimeFormat.format(time));
            System.out.println(TimeFormat.format(time));
        }
    }

    private static void normal2() throws Exception{
        SimpleDateFormat SuccessTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
        String test = "2015-08-28T08:00:00Z";
        SuccessTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date time = SuccessTimeFormat.parse(test);
        if(time!=null) {
            System.out.println(SuccessTimeFormat.format(time));
            System.out.println(TimeFormat.format(time));
        }
    }

    private static void normal3() throws Exception{
        SimpleDateFormat SuccessTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssXXX");
        String test = "2016-09-01 20:00:00-08:00";
        //SuccessTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date time = SuccessTimeFormat.parse(test);
        if(time!=null) {
            System.out.println(SuccessTimeFormat.format(time));//SimpleDateFormat 不处理 夏令时
            System.out.println(TimeFormat.format(time));
        }
    }

    private static void normal4() throws Exception{
        SimpleDateFormat SuccessTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String test = "2016-11-01 20:00:00";
        TimeZone tz = TimeZone.getTimeZone("GMT-08:00");
        SuccessTimeFormat.setTimeZone(tz);
        Date time = SuccessTimeFormat.parse(test);
        if(time!=null) {
            System.out.println(tz.inDaylightTime(time));//timezone 不处理 夏令时 ??? 可以的请看normal5
            System.out.println(SuccessTimeFormat.format(time));
            System.out.println(TimeFormat.format(time));
        }
    }

    private static void normal5() throws Exception{
        //-Duser.timezone=America/North_Dakota/Center
        TimeZone tz = TimeZone.getTimeZone("GMT-08:00");// TimeZone -> ZoneInfo -> ZoneInfoFile to find the tz setup file, if not found,create a custom tz with a few fields being set up(no daylight setup)
        System.out.println("tz: " + tz);

        int offset = tz.getRawOffset();
        System.out.println("raw offset: " + offset);

        int dstSavings = tz.getDSTSavings();
        System.out.println("dstSavings: " + dstSavings);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        String msg = "[" + sdf.format(cal.getTime()) + "] " + cal.getTime();
        msg += ", offset: " + TimeZone.getDefault().getOffset(cal.getTimeInMillis());
        System.out.println(msg);


        SimpleDateFormat SuccessTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String test = "2016-11-01 20:00:00";
        SuccessTimeFormat.setTimeZone(tz);
        Date time = SuccessTimeFormat.parse(test);
        if(time!=null) {
            System.out.println(tz.useDaylightTime());
            System.out.println(SuccessTimeFormat.format(time));
            System.out.println(TimeFormat.format(time));
        }
    }
}
