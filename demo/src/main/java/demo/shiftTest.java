package demo;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/1/3
 * Time: 16:44
 * Desc:
 */
public class shiftTest {

    public static void main(String args[]) {
        System.out.println(doShiftI(35));
        System.out.println(doShiftI(291));
        System.out.println(doShiftL(35));
        System.out.println(doShiftL(291));
        /*
        double f =34.54D;
        f << 4; //can not apply to float
        */
    }

    public static long doShiftI(int shift) {
        return 4 << shift;
    }

    public static long doShiftL(long shift) {
        return 4L << shift;
    }
/**
 * 当我们要位移的基数的类型是long的时候，其实是用64位的RAX寄存器来操作的，
 * 因此存的最大值(2^64-1)会更大，而如果基础是int的话，会用32位的EAX寄存器，
 * 因此能存的最大值(2^32-1)会小点，超过了阈值就会溢出.
 *
 * 使用了8位的CL寄存器来存要位移的位数，因此最大其实就是2^8-1=255啦，
 * 所以上述demo，如果我们将shift的参数从35改成291发现结果是一样的(291=35+256*1)
 * */
}
