/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objectio;
import java.io.*;
import java.math.*;//almost static methods!
class Student implements Serializable{
    private  int id=10;
    private String name;
    private transient String sex;//transient don't serialize the data!
    private static int count=0;//static是类共享的，当该类一个对象被序列化后，这个static变量可能会被另一个对象改变，所以这决定了静态变量是不能序列化的!在类中添加serializeStaticState()和deserializeStaticState()两个static方法！
    public Student(int d,String n,String s){
        id=d;
        name=n;
        sex=s;
        count++;
        System.out.println(id+"is constructing!");
    }
    public String toString(){
        String s=id+" "+name+" "+sex+" "+count;
        return s;
    }
  public static void  serializeStaticState(ObjectOutputStream os) throws IOException {
        os.writeInt(count);
    }
  public static void deserializeStaticState(ObjectInputStream os) throws IOException {
        count = os.readInt();
    }
}
class Teacher implements Externalizable{
    private int id;
    private String name;
    private  String sex;
    public Teacher(int d,String n,String s){
        id=d;
        name=n;
        sex=s;
        System.out.println(id+"is constructing!(int d,String n,String s)");
    }
    public Teacher(){//for Externalizable reconstructor! improtant!
        System.out.println(id+"is constructing!");
    }
    public void writeExternal(ObjectOutput out)throws IOException{// not ObjectOutputStream
        out.writeInt(id);//the order is important!
        out.writeObject(name);
        out.writeObject("Male");
        System.out.println(id+"is storing!");
    }
     public void readExternal(ObjectInput in)throws IOException,ClassNotFoundException{
         id=in.readInt();
         name=(String)in.readObject();
         sex=(String)in.readObject();
         System.out.println(id+"is recovering!");
     }
     public String toString(){
        String s=id+" "+name+" "+sex;
        return s;
    }
}
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        double d=Math.random();//return 0.3 or 0.03 randomly, they are all between 0~1
        d*=100;
        System.out.println(d);
        Student s=new Student(136,"Peter","Male");
        Teacher t=new Teacher(184,"Anne","Femal");
        ObjectOutputStream oout=new ObjectOutputStream(new FileOutputStream("asd.txt"));
        oout.writeObject(s);
        Student ss=new Student(137,"Ken","Male");
        oout.writeObject(ss);
        Student.serializeStaticState(oout);
        oout.writeObject(t);
        System.out.println(s);
        System.out.println(ss);
        System.out.println(t);
        oout.close();
        ObjectInputStream oin=new ObjectInputStream(new FileInputStream("asd.txt"));
        Student s1=(Student)oin.readObject();
        Student ss1=(Student)oin.readObject();
        Student.deserializeStaticState(oin);
        Teacher t1=(Teacher)oin.readObject();
        System.out.println(s1);
        System.out.println(ss1);
        System.out.println(t1);

    }

}
