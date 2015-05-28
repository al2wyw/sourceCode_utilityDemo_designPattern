/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Administrator
 */
class Bowl {

    Bowl(int marker) {
        System.out.println("Bowl(" + marker + ")");
    }

    void f(int marker) {
        System.out.println("f(" + marker + ")");
    }
}

class Table {

    static Bowl b1 = new Bowl(1);

    Table() {
        System.out.println("Table()");
        b2.f(1);
    }

    void f2(int marker) {
        System.out.println("f2(" + marker + ")");
    }
    static Bowl b2 = new Bowl(2);
}

class Cupboard {

    Bowl b3 = new Bowl(3);
    static Bowl b4 = new Bowl(4);

    Cupboard() {
        System.out.println("Cupboard()");
        b4.f(2);
    }

    void f3(int marker) {
        System.out.println("f3(" + marker + ")");
    }
    static Bowl b5 = new Bowl(5);
}

class base{
    private static Bowl b1=new Bowl(1);
    private Bowl b2=new Bowl(2);
    public base(){
        b2 = new Bowl(3);
    }
}
class derive extends base{
    private static Bowl b4=new Bowl(4);
    private Bowl b5=new Bowl(5);
    public derive(){
        super();
        b5 = new Bowl(6);
    }
    {
        b5 = new Bowl(7);
    }
}

class der extends derive {
    private static Bowl b8=new Bowl(8);
    public der(){
        super();
    }
}
public class StaticInitialization {

    public static void main(String[] args) {
        System.out.println(
                "Creating new Cupboard() in main");
        
        new Cupboard();
        
        System.out.println(
                "Creating new Cupboard() in main");
        
        new Cupboard();
        t2.f2(1);
        t3.f3(1);
        
        System.out.println("=======================");
        der d = new der();

        
    }
    static Table t2 = new Table();
    static Cupboard t3 = new Cupboard();
}
