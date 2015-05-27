/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfacepkg;
import java.util.*;

interface Human{//no private, not protected! default is public!
     int I=new Random().nextInt(10);//must assign the value to i! 数据成员时static和final的！它们不能是“空final”，但是可以被非常量表达式初始化!!!
     void play();
     void eat();
     void sleep();
}
abstract class Boy implements Human{//must override all the methods or declare it as abstract
    public void play(){
        System.out.println("he is playing!");
    }
//    public abstract void eat();
//    public abstract void sleep();
}

class Girl implements Human{
    protected int num;
        public void play(){
        System.out.println("she is playing!");
    }
        public void eat(){//void eat() is wrong!
           System.out.println("she is eating!");
        }
        public void sleep(){
             System.out.println("she is sleeping!");
         }
        public void setnum(int h){
            num=h;
        }
}

class A {

    private interface D {

        void f();
    }
    
    interface C{
        void c();
    }

    private class DImp implements D {

        public void f() {
        }
    }

    public class DImp2 implements D {

        public void f() {
        }
    }

    public D getD() {
        return new DImp2();
    }
    private D dRef;

    public void receiveD(D d) {
        dRef = d;
        dRef.f();
    }
}
class B extends A{
    public class Bimp implements C{
        public void c(){}
    }
}
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Human h=new Girl();
        System.out.println(h.I);
        Girl g=new Girl();
        g.setnum(12);
        System.out.println(g.num);
        
        
        A a = new A();
        // Can't access A.D:
        //! A.D ad = a.getD();
        // Doesn't return anything but A.D:
        //! A.DImp2 di2 = a.getD();
        // Cannot access a member of the interface:
        //! a.getD().f();
        // Only another A can do anything with getD():
        A a2 = new A();
        a2.receiveD(a.getD());
        B b=new B();
        B.DImp2 bd=b.new DImp2();
    }

}
