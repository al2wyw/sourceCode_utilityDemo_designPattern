/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
Serializable还有一些方法不懂！
（不知所云）如果序列化流没有将给定类列为要反序列化的对象超类，则 readObjectNoData 方法负责初始化其特定类的对象状态。在接收方使用的反序列化实例类的版本不同于发送方，并且接收者版本扩展的类不是发送者版本扩展的类时，此事可能发生。如果序列化流已经被篡改，也会发生这种情况；因此，不管源流是“敌意的”还是不完整的，readObjectNoData 方法都可以用来正确地初始化反序列化的对象。 
对于没有实现 java.io.Serializable 接口的任何对象，序列化不会对其字段进行读取或赋值。非 serializable 的 Object 的子类可以为 serializable。在此情况下，非 serializable 类必须具有无参数的构造方法以允许其字段能被初始化。在此情况下，子类负责保存和恢复非 serializable 类的状态。经常出现的情况是，该类的字段是可访问的（public、package 或 protected），或者存在可用于恢复状态的 get 和 set 方法。 
Enum 常量的反序列化不同于普通的 serializable 或 externalizable 对象。Enum 常量的序列化形式只包含其名称；不传送常量的字段值。要反序列化 enum 常量，ObjectInputStream 需要从流中读取常量的名称；然后将 enum 常量的基本类型和接收到的常量名称作为参数，调用静态方法 Enum.valueOf(Class, String) 获取反序列化的常量。
 */
package test;
import java.io.*;
class lawer implements Serializable{

    public String name = "xin";
    
}
class myByte extends ByteArrayOutputStream{
    public myByte(){
        
    }
    public myByte(int i){
        super(i);
    }
    public int getSize(){
        return count;
    }
}
/**
 *
 * @author Administrator
 */
public class serializable_copy {
    public static void main(String[] args) throws IOException,ClassNotFoundException{
        lawer l = new lawer();
        myByte bout = new myByte();
        System.out.println(bout.getSize());
        ObjectOutputStream oout = new ObjectOutputStream(bout);
        System.out.println(bout.getSize());
        oout.writeObject(l);
        System.out.println(bout.getSize());
        System.out.println(l.name);
        byte[] out=bout.toByteArray();
        System.out.println(out.length);
        ByteArrayInputStream bin=new ByteArrayInputStream(out);
        ObjectInputStream oin = new ObjectInputStream(bin);
        lawer x=(lawer)oin.readObject();
        System.out.println(l.name);
        System.out.println(x.name);
        oout.close();
        bout.close();
        oin.close();
        bin.close();
    }
}
