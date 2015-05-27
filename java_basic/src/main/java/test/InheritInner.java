/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

class WithInner {

    public int h;

    public WithInner() {
        h = 10;
    }

    public WithInner(int i) {
        h = i;
    }

    class Inner {

        public int j;

        public Inner() {
            j = 20;
        }

        public Inner(int i) {
            j = i;
        }
    }
}

public class InheritInner extends WithInner.Inner {
//    InheritInner() {)// Won't compile
    InheritInner() {
        new WithInner().super();
    } 

    InheritInner(WithInner wi,int i) {
        wi.super(i);
    }

    public static void main(String[] args) {
        WithInner wi = new WithInner();
        InheritInner i=new InheritInner();
        System.out.println(i.j);
        InheritInner ii = new InheritInner(wi,22);
        System.out.println(ii.j);
        Egg2 e2 = new BigEgg2();
        e2.g();
        Egg2.Yolk yolk=e2.new Yolk();
        BigChick bigc=new BigChick(yolk);

    }
}

class Egg2 {

    protected class Yolk {

        public class Chick {
            {System.out.println("Egg2.Yolk.Chick()");}
        }

        public Yolk() {
            System.out.println("Egg2.Yolk()");
        }

        public void f() {
            System.out.println("Egg2.Yolk.f()");
        }
    }
    private Yolk y = new Yolk();

    public Egg2() {
        System.out.println("New Egg2()");
    }

    public void insertYolk(Yolk yy) {
        y = yy;
    }

    public void g() {
        y.f();
    }
}

class BigEgg2 extends Egg2 {

    public class BigChick extends Egg2.Yolk.Chick {

        public BigChick(Egg2.Yolk e) {
            e.super();
            System.out.println("BigEgg2.BigChick(Egg2.Yolk e)");
        }
    }

    public class Yolk extends Egg2.Yolk {

        public Yolk() {
            System.out.println("BigEgg2.Yolk()");
        }

        public void f() {
            System.out.println("BigEgg2.Yolk.f()");
        }
    }

    public BigEgg2() {
        insertYolk(new Yolk());
    }
}

class BigChick extends Egg2.Yolk.Chick {

    public BigChick(Egg2.Yolk e) {
        e.super();
        System.out.println("BigChick()");
    }
}

class BigChick2 extends Egg2 {

    class BigChick3 extends Yolk {

        public BigChick3() {
            System.out.println("BigChick2.BigChick3()");
        }
    }
}

class BigChick3 {

    class BigChick4 extends Egg2.Yolk {

        public BigChick4(Egg2 e) {
            e.super();
            System.out.println("BigChick3.BigChick4()");
        }
    }
}