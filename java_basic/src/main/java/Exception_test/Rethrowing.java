/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//Error and Exception: we donot need to do with error, but we should do with Exception except for runtime-exception
//Exception chain : implement the initCause to save the cause(old Exception) of the new Exception
package Exception_test;

class MyException extends Exception {

    public MyException() {
        super();
    }

    public MyException(String msg) {
        super(msg);
    }
}

class Exception1 extends Exception {

    public Exception1(String s) {
        super(s);
    }
    // public String toString(){
    //      return "It is my Exception!";
    // }
}

public class Rethrowing {

    public static void f() throws Exception {
        System.out.println(
                "originating the exception in f()");
        throw new MyException("thrown from f()");
    }

    public static void g() throws Throwable {
        try {
            f();
        } catch (MyException e) {
            System.out.println(
                    "Inside g(), e.printStackTrace()");
            e.printStackTrace();//the place where the exception is thrown
            throw new Exception(e); //create caused_by
            //throw e; //the trace is not reset and will not change(increase)
            //throw e.fillInStackTrace();//the trace is reset! altought it is throwable type but it is actaully an Exception, when it is caught it will be converted to Exception
            // throw different Exception; //the trace is reset!
        }
    }

    public static void main(String[] args) throws Throwable {
        try {
            g();
        } catch (Exception e) {
            System.out.println(
                    "Caught in main, e.printStackTrace()");
            e.printStackTrace();
        }

    }
}

class ThrowOut {

    public static void main(String[] args) throws Throwable {
        try {
            try {
                int i = 10;
                if (i < 100) {
                    throw new Exception();
                }
            } catch (RuntimeException e) {
                System.out.println("Caught run time");
            }
        } catch (Exception e) {
            System.out.println("Caught in main()");
            e.printStackTrace();
        }
    }
}



class BaseballException extends Exception {}
class Foul extends BaseballException {}
class Strike extends BaseballException {}
class PopFoul extends Foul {}

abstract class Inning {
  Inning()  {}
  void event () throws BaseballException {
   // Doesn't actually have to throw anything
  }
  abstract void atBat() throws Strike, Foul;
  void walk() {} // Throws nothing
}

class StormException extends Exception {}
class RainedOut extends StormException {}

interface Storm {
  void event() throws RainedOut;
  void rainHard() throws RainedOut;
}

class StormyInning extends Inning 
    implements Storm {
  // OK to add new exceptions for constructors,
  // but you must deal with the base constructor
  // exceptions:
  StormyInning() throws RainedOut { System.out.print("test");}
  StormyInning(String s) throws Foul{System.out.print("test with "+s);}
  // Regular methods must conform to base class:
//! void walk() throws PopFoul {} //Compile error
  // Interface CANNOT add exceptions to existing
  // methods from the base class:
//! public void event() throws RainedOut {}
  // If the method doesn't already exist in the
  // base class, the exception is OK:
  public void rainHard() throws RainedOut {}
  // You can choose to not throw any exceptions,
  // even if base version does:
  public void event() {}
  // Overridden methods can throw 
  // inherited exceptions:
  void atBat() throws PopFoul {throw new PopFoul();}
  public static void main(String[] args) {
    try {
      StormyInning si = new StormyInning();
      si.atBat();
    } catch(PopFoul e) {
    } catch(RainedOut e) {
    } catch(BaseballException e) {}
    // Strike not thrown in derived version.
    try {
      // What happens if you upcast?
      Inning i = new StormyInning();
      i.atBat();
      //((StormyInning)i).atBat();
      // You must catch the all the possible exceptions from the base version
      //it is like exception is not dynamic binding
    } catch(RainedOut e) {
    } catch (PopFoul e){    System.out.println("PopFoul!");
    } catch(Foul e){ System.out.println("Foul!");
    } catch(Strike e){System.out.println("Strike!");}
  }
}