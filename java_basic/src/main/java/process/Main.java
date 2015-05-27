/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package process;
import java.io.*;
public class Main {
        public static void main(String[] args) throws IOException
          {
          String command="ping www.baidu.com";
          Runtime r=Runtime.getRuntime();
       Process p=r.exec(command);
         BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
          char[] c=new char[1024];
          int length=0;
          String s;
       while(-1!=(length=br.read(c))){
            s=new String(c,0,length);
         }
         System.out.println(c);
         }
  }

