package demo;

import java.text.DecimalFormat;

/**
 * Created by johnny.ly on 2016/1/25.
 */
public class decimal_parse_test {
    public static void main(String[] args) throws Exception {
        DecimalFormat percentFormat = new DecimalFormat("#.##%");// .##% will be .01%
        double t = 0.0001d;
        System.out.println(percentFormat.format(t));

        DecimalFormat df3 = new DecimalFormat("000.000");

        DecimalFormat df4 = new DecimalFormat("###.###");

        System.out.println(df3.format(12.34));

        System.out.println(df4.format(12.34));
    }
}
