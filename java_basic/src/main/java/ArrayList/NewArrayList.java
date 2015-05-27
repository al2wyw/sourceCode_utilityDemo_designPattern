/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ArrayList;
import java.util.*;
import java.io.*;

class Cat implements Serializable {

    private int catNumber;

    Cat(int i) {
        catNumber = i;
    }

    void print() {
        System.out.println("Cat #" + catNumber);
    }
}

class Dog {

    private int dogNumber;

    Dog(int i) {
        dogNumber = i;
    }

    void print() {
        System.out.println("Dog #" + dogNumber);
    }
}
public class NewArrayList  {
    public static void main(String[] a)throws Exception{
        ArrayList l=new ArrayList();
        for(int i=0;i<10;i++){
                l.add(new Cat(i));
            }
           // l.add(10, new Dog(10));
        Cat c=new Cat(17);
        l.add(c);
        l.set(2, new Cat(10));
        l.remove(0);
        System.out.println(l.contains(c));//string.contains()
        ListIterator li=l.listIterator();
        //l.remove(0);//concurrent modification exception
       // l.add(new Cat(10));//concurrent modification exception
        //before the iteration, no modification! but the listiterator itself can be modified!
        li.add(new Cat(100));//add element before cursor!

        ((Cat)li.previous()).print();
        li.set(new Cat(66));//replace the last element retrieve
        ((Cat)li.next()).print();
        li.remove();//delete the last element retrieve
        while(li.hasNext()){
            ((Cat)li.next()).print();
        }
        File f=new File("asd.txt");
        ObjectOutputStream oout=new ObjectOutputStream(new FileOutputStream(f));
        oout.writeObject(l);//the elements must be serialized!
        oout.close();
        l.remove(0);//no concurrent modification exception
        l.removeAll(l);
        l.clear();//the same as removeAll(l);
        System.out.println(l.isEmpty());//string.isEmpty()
        ArrayList al=new ArrayList();
        Iterator it=al.iterator();
        LinkedList ll=new LinkedList(l);//use arraylist to initialize the linkedlist.
        for(int i=0;i<10;i++){
                ll.add(new Dog(i));
            }
        ll.removeLast();
        ll.addFirst(new Dog(100));
        ListIterator lli=ll.listIterator();
        while(lli.hasNext()){
            ((Dog)lli.next()).print();
        }
    }
}
