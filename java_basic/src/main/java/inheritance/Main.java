/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inheritance;
class animal {

    private int h;

    public animal() {
        System.out.println("I am an defualt animal!");
    }

    public animal(int g) {
        h = g;
        System.out.println("I am an animal!!!!");
    }
    void testLevel(animal a){
        
    }
}

class cat extends animal {

    private int i;

    public cat(int h) {
        //if super() is not invoked, the default constructor of animal will be invoked;
        super(h);
        i = h;
        System.out.println("I am a cat!");
    }
    protected void testLevel(animal a){
        //protected is higher than friendly(default)
    }
}

class live {//复合类与c++不同

    private cat c;

    public live(int h) {
        c = new cat(h);
    }
}

abstract class human {

    abstract public void show();
}

class student extends human {

    public void show() {
        System.out.println("I am student!");
    }
}

class postStudent extends student{
    public void show(){
        System.out.println("I am post-student!");
    }
}

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        cat c = new cat(10);
        c.testLevel(c);//upcasting 向上转型 c is cat and it is also animal!
        animal a = new animal();
        live v = new live(11);
        human h[]={new student(),new postStudent()};
        try{String ss="sdfg"; char ch=ss.charAt(1);}finally{}
        h[0].show();//dynamic binding
        h[1].show();//dynamic binding
    }
}
