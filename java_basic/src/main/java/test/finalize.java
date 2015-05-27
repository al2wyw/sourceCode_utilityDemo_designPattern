/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

class Book {
    boolean checkedOut = false;

    Book(boolean checkOut) {
        checkedOut = checkOut;
    }

    void checkIn() {
        checkedOut = false;
    }

    public void finalize() throws Throwable {
        super.finalize();
        if (checkedOut) {
            System.out.println("Error: checked out");
        }
        System.out.println("Finalized done!");
    }
}

public class finalize {

    public static void main(String[] args) {
        Book novel = new Book(true);
// Proper cleanup:
        novel.checkIn();
        call();
// Drop the reference, forget to clean up:
        new Book(true);
// Force garbage collection & finalization:
        System.gc();
    }
    public static void call(){
        Book novel = new Book(true);
        novel.checkIn();
    }
}
