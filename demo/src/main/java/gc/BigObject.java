package gc;

public class BigObject {

    private static final int KB = 1024;

    private static final int ARRAY_PAYOFF = 8 + 4 + 4; // 64位机器指针大小 + UseCompressedOops + array len

    private static final int OBJECT_PAYOFF = 8 + 4 + 4; // size of BigObject

    private byte[] data = new byte[256 * KB - ARRAY_PAYOFF - OBJECT_PAYOFF];
}
