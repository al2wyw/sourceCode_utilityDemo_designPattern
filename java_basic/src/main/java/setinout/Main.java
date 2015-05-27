/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setinout;

import java.io.*;

/**
 *
 * @author Administrator
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
           FileInputStream in =
                    
                    new FileInputStream(
                    "in.txt");
// Produces deprecation message:
            PrintStream out =
                    new PrintStream(
                    new BufferedOutputStream(
                    new FileOutputStream("out.txt")));
            System.setIn(in);//System.setIn(InputStream);System.setOut(OutputStream);???
            System.setOut(out);
            System.setErr(out);
            BufferedReader br =
                    new BufferedReader(
                    new InputStreamReader(System.in));
            String s;
            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
            out.close(); // Remember this!
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
