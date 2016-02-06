package demo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by johnny.ly on 2015/9/29.
 */
public class date_parse_test {

    public static void main(String[] args) throws Exception{
        SimpleDateFormat SuccessTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String test = "2015-08-28 11:08:03";
        Date time = SuccessTimeFormat.parse(test);
        if(time!=null)
            System.out.println(SuccessTimeFormat.format(time));
    }
}
