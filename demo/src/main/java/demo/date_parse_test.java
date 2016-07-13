package demo;

import java.text.SimpleDateFormat;
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
        System.out.println(TimeZone.getDefault().toString());
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
}
