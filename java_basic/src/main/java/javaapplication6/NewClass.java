package javaapplication6;
class myobject{
    private int i;
    public myobject(int j){
        i = j;
    }
    public void seti(int j){
        i = j;
    }
    public int geti(){
        return i;
    }
}
class mythread implements Runnable{
    protected final myobject i = new myobject(10);//i can not be pointed to another object, but i is mutable, the content of i can be modified
    public void run(){
        synchronized(i){
            
            try {i.wait();}catch(Exception e){}
            System.out.println("Wake up!");
        }
//        synchronized(this){
//            
//            try {this.wait();}catch(Exception e){}
//            System.out.println("Wake up!");
//        }
    }
    public void wake(){
        synchronized(i){
            i.notifyAll();
        }
    }
    public myobject getStr(){
        return i;
    }
}
public class NewClass {
    public static void main(String args[]){
        mythread m = new mythread(); //synchronized(m){m.notify();} can wake up
        //mythread m1 = new mythread(); //synchronized(m1){m1.notify();} can not wake up
        Thread t = new Thread(m);
        t.start();
        myobject i = m.getStr();
        System.out.println(i.hashCode());//three are the same!
        System.out.println(m.getStr().hashCode());
        System.out.println(m.i.hashCode());
         try{Thread.sleep(1000);}catch(Exception e){}//very important!!! without sleep, the following statement maybe run before the thread runs
        synchronized(i){i.notify();}
//        i.seti(50);
//        System.out.println(i.geti());
//        System.out.println(m.getStr().geti());
//       m.wake();
    }
}
