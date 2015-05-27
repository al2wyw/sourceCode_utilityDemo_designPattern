/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.util.*;
/**
 *
 * @author Administrator
 */

class student implements Comparable<student>{
    private int id;
    private String name;
    public student(int i, String n){
        id = i;
        name = n;
    }
    public String toString(){
        return id+" " +name;
    }
//    public int compare(Object s1,Object s2){
//        if(((student)s1).id>((student)s2).id)
//            return 1;
//        else if(((student)s1).id==((student)s2).id)
//            return 0;
//        else
//            return -1;
//    }
    public int compareTo(student s){
        if(id>s.id)
            return 1;
        else if(id==s.id)
            return 0;
        else
            return -1;
    }
    public int hashCode(){
        return id*name.hashCode();
    }
    public boolean equals(Object o){
        if(o instanceof student)
            return id==((student)o).id&&name.equals(((student)o).name);
        else
            return false;
    }
}
public class set {
    public static void main(String[] args){
        Set<student> stu_set = new HashSet<student>();
        Set<student> stu_set2= new TreeSet<student>();
        Set<student> stu_set3= new LinkedHashSet<student>();
        student s1=new student(1,"peter");
        student s2=new student(2,"anme");
        student s3=new student(3,"ken");
        student s4=new student(4,"sue");
        student s5=new student(1,"peter");
        stu_set.add(s1);
        stu_set.add(s2);
        stu_set.add(s3);
        stu_set.add(s4);
        stu_set.add(s5);
        stu_set2.add(s1);
        stu_set2.add(s2);
        stu_set2.add(s3);
        stu_set2.add(s4);
        stu_set2.add(s5);
        stu_set3.add(s1);
        stu_set3.add(s2);
        stu_set3.add(s3);
        stu_set3.add(s4);
        stu_set3.add(s5);
        System.out.println(stu_set);
        System.out.println(stu_set2);
        System.out.println(stu_set3);
        Iterator<student> it=stu_set3.iterator();
        it.next();
        it.next();
        System.out.println(stu_set3);
    }
}

class FailFast {
public static void main(String[] args) {
Collection c = new ArrayList();
c.add("A object");
Iterator it = c.iterator();//产生mutex锁住arraylist
c.add("An object");//改变了arraylisy
// Causes an exception:
String s = (String)it.next();
}
}
