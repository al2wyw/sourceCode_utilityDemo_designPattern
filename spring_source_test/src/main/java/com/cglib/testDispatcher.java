package com.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Dispatcher;
import net.sf.cglib.proxy.Enhancer;

import java.io.File;

/**
 * Created by apple on 23/04/2015.
 */
public class testDispatcher {
    public static void main(String args[]) {

        File f = new File(test.class.getResource("/").getFile());
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, f.getPath());

        final son s = new son();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(son.class);
        enhancer.setCallback(new Dispatcher() {

            @Override
            public Object loadObject() throws Exception {
                return s;
            }
        });
        son proxy = (son) enhancer.create();
        proxy.publicMethodSon("test method interceptor");
        System.out.println(proxy.toString());
    }
}
