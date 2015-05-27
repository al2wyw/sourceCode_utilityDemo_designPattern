/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reflect;

import java.lang.reflect.*;

class Student {

    private String name;
    public int a;
    public int b;
    protected static int count = 0;

    public Student() {
        name = "peter";
        a = 10;
        b = 11;
        count++;
    }

    public Student(String s, int x, int y) {
        name = s;
        a = x;
        b = y;
        count++;
    }

    public int getB() {
        class Graduate {//no prefix declaration! no private...

            private int h;

            public int getAnswer() {
                return a;
            }
        }
        return b;
    }

    public void setA(int h) {
        a = h;
    }

    public static void modifyNum(int h) {
        count = h;
    }

    public String toString() {
        return name + "  " + a + "  " + b + "  " + count;
    }

    public class Inf {

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

    public static class Professor {

        private String name = "inner pro";

        public String toString() {
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

    public static void main(String[] args) {//getClasses,getMethods,getFileds,getConstructors must be public!
        //Class ab=Class.forName("javaapplication2.Student");//equals to Class ab=Student.class;
        Class ab = Student.class;//no need of try-catch!
        Class[] sc = ab.getClasses();
        for (int i = 0; i < sc.length; i++) {
            System.out.println(sc[i]);
        }
        Field[] field = ab.getFields();
        for (int i = 0; i < field.length; i++) {
            System.out.println(field[i]);
        }
        Method[] method = ab.getMethods();
        for (int i = 0; i < method.length; i++) {
            System.out.println(method[i]);
        }
        Constructor<Student>[] structor = ab.getConstructors();
        for (int i = 0; i < structor.length; i++) {
            System.out.println(structor[i]);
        }
        try {
            System.out.println(ab.getMethod("getB"));
            Student sn = (Student) ab.newInstance();//调用无参数构造函数！
        } catch (Exception e) {
            e.printStackTrace();
        }
        Class ac = Person.class;
        Field pfield[] = ac.getFields();
        Student s = new Student("anne", 34, 55);
        boolean isStudent = s instanceof Student;//s instanceof ab is wrong!
        boolean isStudent1 = ab.isInstance(s);//动态检测，更好！
        System.out.println(ab.getSuperclass());
        System.out.println(ac.getSuperclass());
        Integer i = new Integer(11);
        Object[] o = {new String("ken"), new Integer(12), new Integer(13)};//{"ken",new Integer(12),new Integer(13)}
        try {
            field[0].set(s, i);//11 is also ok! int -> Integer
            System.out.println(s);
            field[0].setInt(s, i);
            System.out.println(s);
            field[1].setInt(s, 12345);
            System.out.println(s);
            System.out.println(method[1].getName());//没有任何特定的顺序
            System.out.println(pfield[0].getName());//没有任何特定的顺序
            System.out.println(pfield[1].getName());//没有任何特定的顺序
            System.out.println(pfield[2].getName());//没有任何特定的顺序
            System.out.println("===============");
            System.out.println(structor[1].newInstance(o) + structor[1].getName());//structor[0].newInstance("ken",12,13);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Constructor error!");
        }
        try {
            Class[] t = {String.class, Integer.class, Integer.class};
            Constructor<Student> c = ab.getConstructor(t);
            c.newInstance("234dsfd", 10, 11).getB();
        } catch (Exception e) {
        }
    }
}
