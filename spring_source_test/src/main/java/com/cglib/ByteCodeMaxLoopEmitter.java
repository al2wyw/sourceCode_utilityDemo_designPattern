package com.cglib;

import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.Local;
import net.sf.cglib.core.Signature;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Created with IntelliJ IDEA.
 * User: liyang
 * Date: 2023-07-18
 * Time: 10:07
 * Description:
 */
public class ByteCodeMaxLoopEmitter extends CodeEmitter {

    private boolean closed = false;
    private Local local = null;

    public ByteCodeMaxLoopEmitter(CodeEmitter wrap) {
        super(wrap);
    }

    @Override public void visitInsn(int opcode) {
        if (Opcodes.NOP == opcode) {
            if (closed) {
                load_local(local);
                invoke_static(Type.getType(MaxLoopChecker.class),
                        new Signature("checkLoop", Type.VOID_TYPE, new Type[]{Type.INT_TYPE}));
                iinc(local, 1);
                closed = false;
            } else {
                local = make_local(Type.INT_TYPE);
                push(0);
                store_local(local);
                closed = true;
            }
            return;
        }
        super.visitInsn(opcode);
    }
}
