/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

class OverridingPrivate {

    private final void f() {
        System.out.println("OverridingPrivate.f()");
    }

    private void g() {
        System.out.println("OverridingPrivate.g()");
    }
}

class OverridingPrivate2 extends OverridingPrivate {

    public final void f() {
        g();
        System.out.println("OverridingPrivate2.f()");
    }

    private void g() {
        System.out.println("OverridingPrivate2.g()");
    }
}

public class private_extends {

    public static void main(String[] args) {
        OverridingPrivate2 test = new OverridingPrivate2();
        test.f();
        //test.g();
    }
}

class PrivateOverride {

    private void f() {
        System.out.println("private f()");
    }

    public static void main(String[] args) {
        PrivateOverride po = new Derived();
        po.f(); //no dynamic binding
        //((Derived)po).f();
    }
}
class Derived extends PrivateOverride {

    public void f() {
        System.out.println("public f()");
    }
}
/*
由于private 方法被自动认为就是final 方法，
而且对导出类是屏蔽的。因此，在这种情况下，Derived 类中的f( ) 方法就是一个全新
的方法；既然基类中f( ) 方法在子类 Derived 中不可见，因此也就没有被重载。
 */
