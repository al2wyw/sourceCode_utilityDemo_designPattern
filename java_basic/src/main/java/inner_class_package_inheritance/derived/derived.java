/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inner_class_package_inheritance.derived;
import inner_class_package_inheritance.base.base;

class der extends base{
    //public int pronum; //pronum will hide the pronum from base, print() of base will act differently
    public void test() {
        //frinum = 22;
        pubnum = 20;
        pronum = 21; //proctected can be accessed but friend can not be accessed
        print(); 
        der d= new der();
        int h=d.pronum;
        base b=new base();
        b.print();//b.pronum = 10 is wrong
        //int i=b.pronum; //proctected can be accessed outside the class
    }
    
    protected void proprint(int i){
        proprint();
        System.out.println("der protected print");
    }
    
//    @Override
//    protected void proprint(){
//        System.out.println("der friend print");
//    }
    int friprint(){
        System.out.println("der protected print");
        return 1;
    }
}
public class derived {
    public static void main(String[] args){
            der d=new der();
            d.test();
            //int i=d.pronum; //proctected can be accessed outside the class
            //d.proprint();
            d.proprint(1);
            //((base)d).proprint(); //shit, can not 
    }
}

