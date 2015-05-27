
package write;
import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("file.encoding"));
        char s=306;//store 1 or 2 bytes, converted to unicode
        byte b=49;//store 1 byte range from 0~127, converted to ASCII
        byte bb='1';//store 1 byte
        char c='Ĳ';//store 2 bytes
        char ch='A';//store 1 byte
        char cc='\n';//store 1 byte
        char ccc=65;//store 1 byte, converted to unicode
        String str="asd";//store 3 bytes
        int bs=65842;//sotre 1 or 2 bytes, converted to unicode
        System.out.println((int)c);
       FileOutputStream fout=new FileOutputStream("stream.txt");
       fout.write(ch);
       fout.close();
       int w=50;//store 1 byte,converted to unicode
       FileWriter fw=new FileWriter("writer.txt");
       fw.write(bs);//convert to unicode
       fw.close();
       PrintStream ps=new PrintStream(new File("pstream.txt"),"UTF-8");
       ps.println(bs);//println itself takes up 2 bytes and convert bs into string
       ps.close();
       DataOutputStream dout=new DataOutputStream(new FileOutputStream("dstream.txt"));
       byte[] ctest={1,2,3};
       dout.write(ctest);
       dout.close();
       FileInputStream fin=new FileInputStream("dstream.txt");
       System.out.println(dout.toString());
       int i=1;
       while((i=fin.read())!=-1)
       {
           System.out.println(i);
       }
       System.out.println("done");
    }
}
