/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalexception;

class VeryImportantException extends Exception {

    public String toString() {
        return "A very important exception!";
    }
}

class HoHumException extends Exception {

    public String toString() {
        return "A trivial exception";
    }
}
class MyExc extends Exception{
    public String toString() {
        return "A trivial exception";
    }
    public static void main(String[] args){
        LostMessage lm = new LostMessage();
    try{
            lm.f();
        }
        catch(VeryImportantException e){e.printStackTrace();System.out.println(e);}
        catch(Exception e){e.printStackTrace();}
    }
}

public class LostMessage {

    void f() throws VeryImportantException {
        throw new VeryImportantException();
    }

    void dispose() throws HoHumException {
        throw new HoHumException();
    }

    public static void main(String[] args) throws VeryImportantException,HoHumException{
        LostMessage lm = new LostMessage();
        try {//try block能抛出多少种exception（由方法的throws决定），catch才能而且一定要能接多少种，不能try没抛catch就去接
            lm.f();
        }//catch(VeryImportantException e){e.printStackTrace();}//no catch then the important exception will be lost!
                
        finally {
            lm.dispose();
        }//相比之下，C++把“前一个异常还没处理就抛出下一个异常”的情形看成是糟糕的编程错误。
        //lm.f(); lm.dispose();// the trivial exception will be lost!
        
    }
}
