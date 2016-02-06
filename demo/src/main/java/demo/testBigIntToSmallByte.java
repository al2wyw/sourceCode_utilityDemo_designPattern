package demo;

/**
 * Created by johnny.ly on 2015/11/17.
 */
public class testBigIntToSmallByte {
    public static void main(String[] args) {
        int i = 67108864;
        byte j = (byte) i ;
        System.out.println(j);
        System.out.println((int)(char)(byte)-1);
        System.out.println((int)(byte)-1);
    }
}
