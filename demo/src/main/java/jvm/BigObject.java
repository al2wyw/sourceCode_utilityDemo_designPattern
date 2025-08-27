package jvm;

public class BigObject {

    private byte[] value = new byte[1024*1024];

    private final String name = "BigObject" + System.nanoTime();

    public BigObject() {
        System.out.println(hashCode() + ":" + name + " is born");
    }

    public void print() {
        System.out.println(hashCode() + ":" + name + " print");
    }
}
