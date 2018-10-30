package reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2018/10/30
 * Time: 0:28
 * Desc: -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true
 */
public class ProxyTest {

    public static void main(String[] args) throws Exception{
        Iface iface = (Iface)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{Iface.class,priIface.class},new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName() + " invoked");
                return null;
            }
        });
        iface.doFace(10);//一打断点 toString invoked 就会重复出现 ???
        iface.toString();
    }
}
/*package reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import reflect.proxy.Iface;
import reflect.proxy.priIface;

final class $Proxy0 extends Proxy implements Iface, priIface {
    private static Method m1;
    private static Method m2;
    private static Method m3;
    private static Method m4;
    private static Method m0;

    public $Proxy0(InvocationHandler var1) throws  {
        super(var1);
    }

    public final boolean equals(Object var1) throws  {
        try {
            return ((Boolean)super.h.invoke(this, m1, new Object[]{var1})).booleanValue();
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final String toString() throws  {
        try {
            return (String)super.h.invoke(this, m2, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final Object doFace(int var1) throws  {
        try {
            return (Object)super.h.invoke(this, m3, new Object[]{Integer.valueOf(var1)});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final Object noFace(int var1) throws  {
        try {
            return (Object)super.h.invoke(this, m4, new Object[]{Integer.valueOf(var1)});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final int hashCode() throws  {
        try {
            return ((Integer)super.h.invoke(this, m0, (Object[])null)).intValue();
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[]{Class.forName("java.lang.Object")});
            m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
            m3 = Class.forName("reflect.proxy.Iface").getMethod("doFace", new Class[]{Integer.TYPE});
            m4 = Class.forName("reflect.proxy.priIface").getMethod("noFace", new Class[]{Integer.TYPE});
            m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }
}*/