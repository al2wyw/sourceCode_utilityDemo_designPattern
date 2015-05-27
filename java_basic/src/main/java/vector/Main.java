
package vector;

import java.util.*;

class CrashJava {
public String toString() {
return "CrashJava address: " + this + "\n";//this will call toString(),then unlimited recursion!
}
}
class Cat {

    private int catNumber;

    Cat(int i) {
        catNumber = i;
    }

    void print() {
        System.out.println("Cat #" + catNumber);
    }
}

class Dog {

    private int dogNumber;

    Dog(int i) {
        dogNumber = i;
    }

    void print() {
        System.out.println("Dog #" + dogNumber);
    }
}

public class Main {

    public static void main(String[] args) {
        Vector cats = new Vector();
        for (int i = 0; i < 7; i++) {
            cats.addElement(new Cat(i));
        }
       // Not a problem to add a dog to cats:
       // cats.addElement(new Dog(7));//runtime error!
        for (int i = 0; i < cats.size(); i++) {
            ((Cat) cats.elementAt(i)).print();
        }
        Cat cc=(Cat)cats.elementAt(0);
        cc.print();
        cats.insertElementAt(new Cat(100), 1);//object first! index later!
        Enumeration e = cats.elements();
        while(e.hasMoreElements())
        ((Cat)e.nextElement()).print();
        System.out.println(cats);
        System.out.println(e);
        String str=new String();
        System.out.println(str.isEmpty());
    }
}
