/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.io.*;
/**
 *
 * @author root
 */
public class skip {
    public static void main(String[] args)throws Exception{
        FileOutputStream fout = new FileOutputStream("123.txt");
        byte[] b = {34,4,5,34,54,65,45};
        fout.write(b);
        FileInputStream fin = new FileInputStream("123.txt");
        fin.skip(5);
        int i=fin.read();
        System.out.println(i);//65
        FileWriter fw = new FileWriter("1234.txt");
        char[] bb = {'3',34565,'d','6','n','v','5'};
        fw.write(bb);
        fw.write('!');
        fw.write(65);//transformed to 'A'
        fw.flush();//without this 1234.txt is empty!!!
        FileReader fr = new FileReader("1234.txt");
        fr.skip(5);
        char j=(char)fr.read();
        System.out.println(j);//v
        
    }
}
