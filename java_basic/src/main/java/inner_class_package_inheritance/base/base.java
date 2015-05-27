/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package inner_class_package_inheritance.base;
/**
 *
 * @author Administrator
 */
public class base {
    public int pubnum=2;
    protected int pronum=3;
    int frinum=4;
    public void print(){
        System.out.println(pubnum+" "+pronum+" "+frinum);
    }
    protected void proprint(){
        System.out.println("base protected print");
    }
    void friprint(){
        System.out.println("base friend print");
    }
}
class test{
    public static void main(String[] args){
        base b=new base();
        b.pubnum=10;
        b.pronum=11;
        b.frinum=12;
        b.print();
    }
}
