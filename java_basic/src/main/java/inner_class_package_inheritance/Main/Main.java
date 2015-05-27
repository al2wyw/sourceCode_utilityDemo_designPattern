/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inner_class_package_inheritance.Main;
import java.util.Arrays;
import Main.Student.*;
class Professor{//it is ok!
        private String name="norm pro";
        public String toString(){
            return name;
        }
        public abstract class test{
            private int i=10;
            public abstract void cando();
        }
        public interface test1{
            //interface 的全部成员必须是 public
            int count=10;
            void dojob();
        }
}
 class Inf {

        protected int idnum;
        protected String name;
        protected String sex;

        public Inf() {
            idnum = 123;
            name = "peter";
            sex = "male";
        }

        public boolean isMan() {
            if (sex.equals("male")) {
                return true;
            } else {
                return false;
            }
        }
    }

class Student {

    int a;
    protected int b;
    public Student() {
        a = 10;
        b = 11;
    }

    public int getB() {
        class Graduate {//no prefix declaration! no private...

            private int h;

            public int getAnswer() {
                return a;
            }
        }
        int j = 34;
        return j;
    }
//    private static Inf in=new Inf(); //can not do this!!!!!!!!!!!!!! most important!!!!
    private static Inf in=new Student().new Inf();
    protected static Stest st=new Stest();
    public static Professor spro=new Professor();

    public class Inf { //hide the outer Inf class
        int a;
        protected int idnum;
        protected String name;
        protected String sex;
        //static int count;//can not own static

        public Inf() {
            a = 100000;
            idnum = 123;
            name = "peter";
            sex = "male";
        }

        public boolean isMan() {
            if (sex.equals("male")) {
                return true;
            } else {
                return false;
            }
        }
        public String toString(){
            String str;
            str = Student.this.a+" "+ b + " "+name;
            return str;
        }
    }
    
    public void testinner(){
        Inf n = new Inf();
    }
    
    public static void testinner1(){
        Student s = new Student();
        Inf n = s.new Inf();
    }
    //public void setInfName(){name="sdf";}//外部方法无法访问内部类的property！
    public static class Stest{//can contian non-static property or method!
        private String name="inner pro";
        public String toString(){
            return name;
        }
    }
}

class Person extends Student {

    public int d = 54;

    public Person() {
        super();
    }

    public int setA() {
        int g = 39;
        return g;
    }
}



public class Main {

    public static void main(String[] args) throws Exception{
        Student s = new Student();
        s.b = 12;//s can not call the methods of inf class
        Student.Inf in = s.new Inf();// in can not call the methods of student class
        System.out.println(s.toString());
        if (in.isMan()) {
            System.out.println("He is a man!");
        }
        Inf inf=new Inf();
        Person p = new Person();
        p.b = 12;
        Student ls = new Person();
        System.out.println(ls.getB());
        System.out.println(s.b);
        System.out.println(s.getB());
        System.out.println(p.setA());
        int[] a = new int[]{1, 2, 5, 6, 7};
        for (int i = 0; i < 5; i++) {
            System.out.println(a[i]);
        }
        String st = "fuck you!";
        System.out.println(st);
        String str = "asdf";
        byte[] bs;
        bs = str.getBytes();
        int index = str.indexOf('f');
        System.out.println(index);
        System.out.println(bs.length);
        Integer ing = new Integer(143256);
        System.out.println(ing);//隐性调用toString()
        MyString string = new MyString("asdzxc");
        System.out.println(string);
        string.setString("qwerty");
        System.out.println(string);
        MyString sss = (MyString) string.clone();
        System.out.println(sss);
        if (sss == string) {
            System.out.println("it is so crazy!");
        }
        String string1 = "dsfef";
        System.out.println(string1.concat("jkl"));
        MyString2 string2 = new MyString2("crazy", 10);
        MyString2 string22 = (MyString2) string2.clone();//clone is derived!
        System.out.println(string22);
        string22.print();
        boolean flag = string1.matches("d");
        System.out.println(flag);
         System.gc();
         System.runFinalization();
         Professor pr=new Professor();
         System.out.println(pr);
         Stest pro=new Stest();
         System.out.println(pro);
         MyString mystring=new MyString("sdf");
         MyString2 mystring2=new MyString2("df",3);
         //mystring2=(MyString2)mystring;//no error but exception!
         mystring2.setString("vb");
         //(MyString)mystring2=mystring//在c++可以，为什么这里不行？
         int i=10000; byte b=1;
         //(int)b=i;//wrong!
         b=(byte)i;
         System.out.println(b);
    }
}


//following are all cloneable
class MyString implements Cloneable {

    private String str;

    public MyString(String s) {
        str = s;
    }

    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (Exception e) {
        }
        return o;
    }

    public void setString(String s) {
        str = s;
    }

    public String toString() {
        return str;
    }
}

class MyString2 extends MyString {

    private int i;

    public MyString2(String s, int f) {
        super(s);
        i = f;
    }

    public void setString(String s, int f) {
        super.setString(s);
        i = f;
    }

    public void print() {
        System.out.println(super.toString());//System.out.println(this) is ok, but System.out.println(super) is wrong
        System.out.println(i);
    }
}

class MyString3 extends MyString2 {

    private String name;

    public MyString3(String s, int f, String ss) {
        super(s, f);
        name = ss;
    }

    public void print() {
        super.print();
        System.out.println(name);
    }
}

class DepthReading implements Cloneable {

    private double depth;

    public DepthReading(double depth) {
        this.depth = depth;
    }

    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}

class TemperatureReading implements Cloneable {//复合类的cloneable！

    private long time;
    private double temperature;

    public TemperatureReading(double temperature) {
        time = System.currentTimeMillis();
                this.temperature = temperature;
    }

    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}

class OceanReading implements Cloneable {

    private DepthReading depth;
    private TemperatureReading temperature;

    public OceanReading(double tdata, double ddata) {
        temperature = new TemperatureReading(tdata);
        depth = new DepthReading(ddata);
    }

    public Object clone() {
        OceanReading o = null;
        try {
            o = (OceanReading) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        // Must clone handles:
        o.depth = (DepthReading) o.depth.clone();
        o.temperature = (TemperatureReading) o.temperature.clone();
        return o; // Upcasts back to Object
    }
}