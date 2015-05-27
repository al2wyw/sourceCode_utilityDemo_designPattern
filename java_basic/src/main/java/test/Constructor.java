package test;

//最多只有一个public class也可以没有public class
class test {

    private int i;
    private final int j;//this is blank final!!! special!!

    public test(int n) {
        System.out.println(i);//System.out.println(j); is wrong, because j will not initialized automatically
        j = 1;
        System.out.println(n + " is tested!");
        print();//危险啊！！！ 最好只调用private的member function或者final的member function
    }
    public void print(){
    System.out.println( "test print is tested!");
}
};
class test1 extends test{
    private int j;
    public test1(){
        this(100);
    }
    public test1(int n) {
        super(n);//这里的print（）被override了，已经不是基类的print（）！！！
        j = 1;
    }
    public void print(){
        System.out.println( "test1 print is tested!");
    }
};
class real {//only if when the memory is allocated to the reference, the default initialization is invoked! 

    private test t1 = new test(10);

    public real(int n) {
        t3 = new test(n);
    }
    private test t3 = new test(20);
};

class flower {

    private int i;
    private String s;
    private static real r;
    public static int count;

    static {//静态块专用于初始化静态member
        count = 0;
        r = new real(30);
    }

    {
        i = 10;
        s = "这样初始化，很骚！";
        System.out.println("constructor block");
    }

    public flower(int n) {
        i = n;
    }

    public flower(String ss) {
        s = ss;
    }

    public flower(int n, String ss) {
        this(n); //only ONE this() constructor can be put at the FIRST place of function
                //super() must be placed at the FIRST place too
        s = ss;
    }

    public static void print(){
        
    }
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Collectiong garbage!");
    }
    
    //protected void toString(){}//Object 有个public 方法toString， 所以要override toString的话必须不低于public！
    
    
};

class testFlower{
    public  static void main(String[] arg) throws Exception{
        System.out.println("What is available for you?");
        flower e;
        Class d=flower.class;
        flower.print();//load the class
        System.out.println("What is available for you?");
        flower.count=10;//load the class
        System.out.println("What is available for you?");
        //Class.forName("test.flower");//load the class
        boolean b = true;
        System.out.println(b);
    }
}

public class Constructor {
    public static void main(String[] args) {
        //real t = new real(80);//default initialization is invoked!
        flower f = new flower(20, "I love you");
        flower d = new flower(20, "I love you");
        System.out.println(flower.count);
        d = null;
        test1 tt= new test1(2);
        System.gc();
    }
}
