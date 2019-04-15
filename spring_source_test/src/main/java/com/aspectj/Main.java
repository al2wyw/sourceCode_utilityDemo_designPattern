package com.aspectj;

import org.apache.commons.io.IOUtils;
import org.aspectj.weaver.loadtime.ClassPreProcessorAgentAdapter;

import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/4/15
 * Time: 11:35
 * Desc:
 */
public class Main {

    public static void main( String args[] ) throws Exception{
        URLClassLoader appLoader = (URLClassLoader)Thread.currentThread().getContextClassLoader();
        URL[] target = appLoader.getURLs();
        WeaverClassLoader loader = new WeaverClassLoader(target, null);
        Class klass = loader.loadClass("com.aspectj.Boot");
        Method method = klass.getMethod("main", String[].class);
        method.invoke(null,new Object[]{args});
    }

    private static class WeaverClassLoader extends URLClassLoader{

        private ClassFileTransformer classFileTransformer = new ClassPreProcessorAgentAdapter();

        public WeaverClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                String path = name.replace('.', '/').concat(".class");
                if (name.startsWith("org.aspectj") || name.startsWith("org/aspectj")) {//skip aspectj
                    return super.findClass(name);
                }
                InputStream inputStream = getResourceAsStream(path);
                byte[] content = IOUtils.toByteArray(inputStream);
                IOUtils.closeQuietly(inputStream);

                byte[] newContent = classFileTransformer.transform(this, name, null, null, content);
                return defineClass(name, newContent, 0, newContent.length);
            }catch (Exception e){
                e.printStackTrace();
            }
            return super.findClass(name);
        }
    }
}
