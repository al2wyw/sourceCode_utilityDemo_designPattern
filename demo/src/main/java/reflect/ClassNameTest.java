package reflect;

import reflect.name.GetName;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/10/16
 * Time: 15:41
 * Desc:
 */
public class ClassNameTest {
    public static void main(String args[]) throws Exception{
        URL url = GetName.class.getResource("");
        System.out.println(url.getFile());
        File file = new File(url.getFile());
        if(file.isDirectory()){
            File[] files = file.listFiles();
            Arrays.asList(files).forEach(file1 -> System.out.println(file1.getName()));
        }
        Class klass = GetName.InnerName.class;
        print(klass);
        klass = GetName.MemberInnerName.class;
        print(klass);
        klass = GetName.class;
        Object o = klass.newInstance();
        Method m = klass.getMethod("test",String[].class);
        m.invoke(o,(Object)null);

        //GetName.InnerName innerName = new GetName.InnerName();
        //GetName getName = new GetName();
        //GetName.MemberInnerName memberInnerName = getName.new MemberInnerName();
    }

    public static void print(Class klass){
        System.out.println(klass.getName() + " " + klass.getSimpleName() + " " + klass.getTypeName() + " " + klass.getCanonicalName());
        System.out.println(klass.getEnclosingClass() + " " + klass.getDeclaringClass());
    }
}
