package demo;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by johnny.ly on 2016/7/27.
 */
public class testCurrency {

    private static final String BOOKING_OVERTIME_SUBJECT = "{test}";

    private static final String BOOKING_OVERTIME_CONTENT = "{0} start to go to {1} and still {2}";

    public static void main(String[] args){
        long tid = 2345432432432L;
        String vendor = "test";
        String subject = BOOKING_OVERTIME_SUBJECT;
        String content = MessageFormat.format(BOOKING_OVERTIME_CONTENT, String.valueOf(tid), vendor, tid);
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        String symbol = Currency.getInstance("USD").getSymbol(Locale.US);//还要一个locale.US
        //format.setCurrency(Currency.getInstance(Locale.CHINA));//只是set currency没有用！估计底层也是调用currency 的 getSymbol
        System.out.println(subject+"\n"+content+"\n"+format.format(3434.54D)+symbol);
    }
}
