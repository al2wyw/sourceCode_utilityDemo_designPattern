package serialize;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/9/27
 * Time: 22:13
 * Desc:
 */
public class Main {
    public static void main(String[] args) throws Exception{
        writeObject();
        readObject(); //delete ExtData class, this will throw class not found
    }

    private static void writeObject() throws Exception{
        ExtData extData = new ExtData();
        extData.setI(10);
        extData.setD(34.23);
        extData.setName("test");
        extData.setExt("ext");
        FileOutputStream fout = new FileOutputStream("test.txt");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        BaseData baseData = extData;
        out.writeObject(baseData);
        out.close();
        fout.close();
    }

    private static void readObject() throws Exception{
        FileInputStream fin = new FileInputStream("test.txt");
        ObjectInputStream oin = new ObjectInputStream(fin);
        BaseData baseData = (BaseData)oin.readObject();
        System.out.println(baseData.getName());
        oin.close();
        fin.close();
    }
}
