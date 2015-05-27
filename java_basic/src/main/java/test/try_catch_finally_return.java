/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Administrator
 */
class myExecpt extends Exception{
    public myExecpt(String s){
        super(s);
    }
}
class testExceptionCast{
    class myExecpt2 extends myExecpt{
    public myExecpt2(String s){
        super(s);
    }
    }
    public myExecpt throwE(){
        return new myExecpt2("exception 2");//exception会被强制转换downcast
    }
    public static void main(String[] args){
        try{
            testExceptionCast e=new testExceptionCast();
            throw e.throwE();
        }catch(myExecpt2 e){System.out.println("caught myExecpt2");}
        catch(myExecpt e){System.out.println("caught myExecpt");}
    }
}
class test_dont_catch{
    public static void main(String[] args) throws Exception{
        try{
            int i=10;
            if(i<100)
                throw new Exception("exception ok");
            if(i<10)
                throw new myExecpt("my except");
        }catch(testExceptionCast.myExecpt2 e){System.out.println(e.getMessage());}
        catch(myExecpt e){System.out.println(e.getMessage());}
        throw new Exception("exception bad");//will not execute!!!
    }
}
public class try_catch_finally_return {
    public int test() throws Exception {
         try{
             int i = 1;
             if(i<10)
                 throw new Exception("sdfdf"){
                     public String getMessage(){
                         return "Fuck, autonomous class";
                     }
                 };
             System.out.println("normal");
             //return i;
         }catch(Exception i){
             System.out.println(i.getMessage()+"  asdf");
             throw i;//the last return statement will not run, but it is OK!
             //return 10; //it is not OK!!!
         }finally{//before return and throw or after not throw, it will run!
             System.out.println("finally runs!");
         }
        return 100;
    }
     public static void main(String[] args){
         try_catch_finally_return a = new try_catch_finally_return();
         try{
          System.out.println(a.test());
         }catch(Exception i){
             System.out.println("main");
         }
     }
}
