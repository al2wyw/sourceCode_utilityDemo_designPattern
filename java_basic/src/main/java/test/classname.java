/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
interface face{
    void test();
}
/**
 *
 * @author Administrator
 */
public class classname {
    public class inner implements face{
        public void test(){
            
        }
    }
    public inner getclass(){
        return new inner(){
            
        };
    }
    public void test(){
        class localinner {
            
        }
    }
    public static void main(String[] args){
        
    }
}

