/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.io.*;
/**
 *
 * @author Administrator
 */
//test write transient data
public class externalize implements Externalizable {
    private int i;
    private String s;
    private transient float f;
    public externalize(){
           i = 10;
           s = "sdfdsfsdf";
           f = 234.34f;
    }
    public externalize(float d){
           f = d;
    }
    public void writeExternal(ObjectOutput o)throws IOException{
        o.writeObject(s);
        o.writeInt(i);
        o.writeFloat(f);
    }
    public void readExternal(ObjectInput h)throws IOException, ClassNotFoundException{
        s = (String)h.readObject();
        i = h.readInt();
        f = h.readFloat();
    }
    
    public String toString(){
        
        return " "+f+" ";
    }
     public static void main(String[] args)throws IOException, ClassNotFoundException{
         externalize e=new externalize(534.34f);
         ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("123.txt"));
         o.writeObject(e);
         o.close();
         ObjectInputStream i = new ObjectInputStream(new FileInputStream("123.txt"));
         externalize h=(externalize)i.readObject();
         System.out.println(h);
     }
}
