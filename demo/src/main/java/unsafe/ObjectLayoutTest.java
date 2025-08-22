package unsafe;

import org.openjdk.jol.info.ClassLayout;

public class ObjectLayoutTest
{
    public static void main(final String[] args) throws Exception {
        ClassLayout out = ClassLayout.parseClass(ArithmeticException.class);
        System.out.println(out.toPrintable());
    }
}
