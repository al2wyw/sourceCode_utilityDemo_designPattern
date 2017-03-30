package demo;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/3/30
 * Time: 11:15
 * Desc:
 * CopyOnWrite: read write separate! no read lock, just write lock. spare of lock conflict.
 * Scene: read more than write a lot
 * Drawback: shallow copy memory use and data inconsistency(write data can not be read util setArray)
 */
public class ArrayCopyTest {
    public static void main(String[] args) {
        NameValue[] instance1 = {
                new NameValue("name1", 1),
                new NameValue("name2", 2),
                new NameValue("name3", 3),
        };
        NameValue[] instance2 = new NameValue[instance1.length];

        // Print initial state
        System.out.println("Arrays before shallow copy:");
        System.out.println("Instance 1: " + Arrays.toString(instance1));
        System.out.println("Instance 2: " + Arrays.toString(instance2));

        // Perform shallow copy
        System.arraycopy(instance1, 0, instance2, 0, 3);

        // Change instance 1
        for (int i = 0; i < 3; i++) {
            instance1[i].change();
        }

        // Print final state
        System.out.println("Arrays after shallow copy:");
        System.out.println("Instance 1: " + Arrays.toString(instance1));
        System.out.println("Instance 2: " + Arrays.toString(instance2));

        testCopy();
    }

    private static void testCopy(){
        NameValue[] instance1 = {
                new NameValue("name1", 1),
                new NameValue("name2", 2),
                new NameValue("name3", 3),
        };
        NameValue[] instance2 = new NameValue[instance1.length];

        // Print initial state
        System.out.println("Arrays before deep copy:");
        System.out.println("Instance 1: " + Arrays.toString(instance1));
        System.out.println("Instance 2: " + Arrays.toString(instance2));

        // Perform shallow copy
        instance2 = Arrays.copyOf(instance1, 3);

        // Change instance 1
        for (int i = 0; i < 3; i++) {
            instance2[i].change();
        }

        // Print final state
        System.out.println("Arrays after deep copy:");
        System.out.println("Instance 1: " + Arrays.toString(instance1));
        System.out.println("Instance 2: " + Arrays.toString(instance2));
    }

    private static class NameValue {
        private String name;
        private int value;

        public NameValue(String name, int value) {
            super();
            this.name = name;
            this.value = value;
        }

        public void change() {
            this.name = this.name + "-bis";
            this.value = this.value + 1;
        }

        @Override
        public String toString() {
            return this.name + ": " + this.value;
        }
    }
}
