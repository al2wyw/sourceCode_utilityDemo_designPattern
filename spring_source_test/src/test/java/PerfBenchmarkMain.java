import com.cglib.ByteCodeMaxLoopAnalysis;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import org.apache.tools.ant.taskdefs.Classloader;

/**
 * Created with IntelliJ IDEA.
 * User: liyang
 * Date: 2023-07-20
 * Time: 19:48
 * Description:
 */
public class PerfBenchmarkMain {

    public static void main(final String[] args) throws Exception {
        Class<?> klass = new MyDefineClassLoader((URLClassLoader)Thread.currentThread().getContextClassLoader()).loadClass("com.test.PerfBenchmark");
        System.out.println(klass.getClassLoader());
        Method method = klass.getDeclaredMethod("main", String[].class);
        method.invoke(null, (Object) new String[] {});
    }

    public static class MyDefineClassLoader extends URLClassLoader {

        public MyDefineClassLoader(URLClassLoader parent) {
            super(parent.getURLs(), parent.getParent());//屏蔽掉AppClassLoader，由MyDefineClassLoader代替它
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            //jmh的Benchmark方法被额外的AppClassLoader来加载
            if (name.contains("InstructionsTestG")) {
                try {
                    byte[] newContent = ByteCodeMaxLoopAnalysis.transform("com/dynamicInvoke/InstructionsTest.class",
                            "com.dynamicInvoke.InstructionsTestG");
                    return defineClass("com.dynamicInvoke.InstructionsTestG", newContent, 0, newContent.length);
                } catch (Exception e) {
                    throw new ClassNotFoundException();
                }
            }
            return super.loadClass(name);
        }
    }
}
