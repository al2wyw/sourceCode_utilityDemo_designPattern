/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inner_class_package_inheritance.innerclass;

import innerclass.Parcel3.PDestination;
import innerclass.Parcel4.PDestination4;

abstract class Contents {

    abstract public int value();
}

interface Destination {

    String readLabel();
}

public class Parcel3 {

    private int i = 10;

    public static class StaticDestination implements Destination {

        private String label;
        private static int i = 10;

        public StaticDestination(String s) {
            label = s;
        }

        public String readLabel() {
            return label;
        }
    }

    private class PContents extends Contents {

        private int i = 11;

        public int value() {
            return Parcel3.this.i;
        }
    }

    protected class PDestination
            implements Destination {

        private String label;

        public PDestination(String whereTo) {
            label = whereTo;
        }

        public String readLabel() {
            return label;
        }
    }

    public void testInner() {
        PDestination pd = new PDestination("ShenZhen");
        pd.label = "sdf";
        System.out.println(pd);
    }

    public static void testInner2() {
        Parcel3 p = new Parcel3();
        PDestination pd = p.new PDestination("ShenZhen");
        pd.label = "sdf";
        System.out.println(pd);
    }

    public void testStaticInner() {
        StaticDestination.i = 100;
        StaticDestination pd = new StaticDestination("Shenzhen");
        pd.label = "sdf";
    }

    public static void testStaticInner2() {
        StaticDestination.i = 100;
        StaticDestination pd = new StaticDestination("Shenzhen");
        pd.label = "sdf";
    }

    public Destination dest(String s) {
        return new PDestination(s);
    }

    public Contents cont() {
        return new PContents();
    }
}

class Parcel4 extends Parcel3 {

    protected class PDestination4
            extends PDestination {//Parcel3.PDestination也可以！can not override Parcel3.PDestination!

        private int i = 10;

        public PDestination4(String whereTo) {
            super(whereTo);
        }
    }
    
    public PDestination getPDest(){
        return new PDestination("shenzhen");
    }
}

class Parcel5 {

    public Contents cont(final String x) {//访问外部类的局部变量(即方法内的变量)，但是变量必须是final的
        final int jjj = 10;
        return new Contents() {//anonymous inner class

            private String i = x;
            private int j = jjj;

            public int value() {
                return 1;
            }
        }; //class MyContents extends Contents {
        //private int i = 11;
        //public int value() { return i; }
        //}
        //return new MyContents();
    }
}

class Parcel6 {

    public Destination dest(final String dest, final float price) {
        return new Destination() {

            private int cost;
            // Instance initialization for each object:

            {//anonymous constructor!
                cost = Math.round(price);
                if (cost > 100) {
                    System.out.println("Over budget!");
                }
            }
            private String label;

            public String readLabel() {
                return label;
            }

            {// all anonymous constructors should be run!
                System.out.println("two constructors!");
                label = dest;
            }
        };
    }
}

class Test {

    public static void main(String[] args) {
        Parcel3 p = new Parcel3();
        Contents c = p.cont();
        Destination d = p.dest("Tanzania");
        System.out.println(c);
        System.out.println(d);
        PDestination dd = p.new PDestination("Corrimal");
        System.out.println(dd);
        Parcel4 p4 = new Parcel4();
        PDestination4 ddd = p4.new PDestination4("Prince");
        System.out.println(ddd);
        int i = Math.round(1.53F);//1.53 is wrong. 1.53 will be compiled to 1.53D
        int j = Math.abs(2147483647);//35 will be compiled to integer. no 35b,35s! 35l is available!
        byte h = 127;//0111 1111 is the biggest positive number!
        System.out.println(Math.round(1.53d));
        System.out.println(Math.floor(1.53));
        Parcel6 p6 = new Parcel6();
        Destination d6 = p6.dest("parcel6 is sent!", 100.54f);//100.54 is default double!
        System.out.println(d6.readLabel());
    }
}


