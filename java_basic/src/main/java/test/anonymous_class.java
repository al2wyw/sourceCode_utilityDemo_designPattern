/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

class in{
    private int i;
    private String s;
    public in(int j){
        i = 10;
        s = "sdfsdf";
    }
    public String toString(){
        return s;
    }
}

class ttt{
    private int j;
    public ttt(){
        j = 100;
    }
    public in createIn(int i){
        int h = i;
        return new in(h){//no in(i)! no matching constructor
            //public in(int i){//no any other constructor
               
            //}
             public void check(){
                     //m = i + 10; // i must be final
                }
             private int m;
        };
    }
}
public class anonymous_class {
    public static void main(String[] args){
        
    }
}
