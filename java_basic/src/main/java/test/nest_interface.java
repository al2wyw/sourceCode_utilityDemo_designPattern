/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//have a look at interface&Exception's interfacepkg package
package test;

class A {

    interface B {

        void f();
    }

    public interface C {

        void f();
    }
    
    private interface D { //the private interface prevent upstream casting

        void f();
    }

    
    public class BImp implements B {

        public void f() {
        }
    }

    private class BImp2 implements B {

        public void f() {
        }
    }

   
    class CImp implements C {

        public void f() {
        }
    }

    private class CImp2 implements C {

        public void f() {
        }
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

interface E {

    interface G { //default is public interface

        void f();
    }
    // Redundant "public":

    public interface H {

        void f();
    }

    void g();
    // Cannot be private within an interface:
    //! private interface I {}
}

public class nest_interface {

    public class BImp implements A.B {

        public void f() {
        }
    }

    class CImp implements A.C {

        public void f() {
        }
    }
    // Cannot implement a private interface except
    // within that interface's defining class:
    //! class DImp implements A.D {
    //!  public void f() {}
    //! }

    class EImp implements E {

        public void g() {
        }
    }

    class EGImp implements E.G {

        public void f() {
        }
    }

    class EImp2 implements E {

        public void g() {
        }

        class EG implements G {

            public void f() {
            }
        }
    }

    public static void main(String[] args) {
        A a = new A();
        // Can't access A.D:
        //! A.D ad = a.getD();
        // Doesn't return anything but A.D:
        //! A.DImp2 di2 = a.getD();
        // Cannot access a member of the interface:
        //! a.getD().f();
        // Only another A can do anything with getD():
        A.DImp2 d2 = a.new DImp2();
        d2.f(); // f() from private interface
        A a2 = new A();
        a2.receiveD(a.getD());
        String t= "test";
        System.out.println(t.replaceAll("te\\w", "a"));
    }
} ///:~

