package demo;

/**
 * Created by johnny.ly on 2016/5/17.
 */
public class testFloatCompare {
    /**
     *  float: +  -  *  /  may not precise , =  !=   >  <  may not precise
     * */
    public static void main(String[] args){
        double dou = Double.MAX_VALUE;
        if(dou == Double.MAX_VALUE){
            System.out.println("equal");
        }
        double less = 12.34D;
        if(less<dou){
            System.out.println("less");
        }
        double s=0d;
        for (int i=0; i<26; i++)
            s += 0.1d;
        System.out.println(s);//2.600000000000001
        if(s>2.6){
            System.out.println("larger");
        }

        double d = 29.0 * 0.01;
        System.out.println(d);
        System.out.println( (d * 100));
        System.out.println( (int)(d * 100));

        /**
         * 计算机表示浮点数(float或double类型)都有一个精度限制，
         * 对于超出了精度限制的浮点数，计算机会把它们的精度之外的小数部分截断。
         * 因此，本来不相等的两个浮点数在计算机中可能就变成相等的了
         * */
        float a = 10.222222225f;
        float b = 10.222222229f;
        if(a>b){
            System.out.println("a>b");
        }else if(a<b){
            System.out.println("a<b");
        }else if(a==b){
            System.out.println("a==b");
        }

        System.out.println(0.05 + 0.01);
        System.out.println(1.0 - 0.42);
        System.out.println(4.015 * 100);
        System.out.println(123.3 / 100);

        /**
         * new BigDecimal(0.1) 所创建的 BigDecimal 正好等于 0.1（非标度值 1，其标度为 1），
         * 但是它实际上等于 0.1000000000000000055511151231257827021181583404541015625
         * 谨慎使用这种构造函数
        * */
    }
}
