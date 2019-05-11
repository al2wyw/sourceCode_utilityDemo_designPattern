package unsafe;

import java.lang.instrument.Instrumentation;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/5/10
 * Time: 11:10
 * Desc:
 */
public class InstrumentationSteal {

    public static Instrumentation instrumentation;

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Agent.premain");
        instrumentation =inst;
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("Agent.agentmain");
        instrumentation =inst;
    }
}
