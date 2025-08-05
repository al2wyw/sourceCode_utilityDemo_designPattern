package gc;

import demoObject.BigObject;
import demoObject.SmallObject;
import utils.ThreadUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by johnny.ly on 2016/6/10.
 */
public class testNewArea {

    //private static List<MyObject> objects = new LinkedList<MyObject>();

    private static List<SmallObject> object1s = new LinkedList<SmallObject>();

    public static void main(String[] args) throws Exception{
        ThreadUtils.sleep(10000);

        object1s.add(new SmallObject());
        object1s.add(new SmallObject());
        object1s.add(new SmallObject());

        for(int i=0;i<1000;i++){
            BigObject o = new BigObject();
            System.out.println(o);
            o = null;
            //ThreadUtils.sleep(3000);
            LockSupport.parkNanos(3000000000L);
        }
    }
}

/**
 * -Xms12M
 * -Xmx12M
 * -Xmn6M
 * -XX:SurvivorRatio=2
 * -XX:MaxTenuringThreshold=5
 *
 * gdb --args ~/linux-x86_64-normal-server-slowdebug/jdk/bin/java -Xcomp -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=compileonly,*testNewArea.* -XX:+LogCompilation -XX:LogFile=./mylogfile.log -XX:+PrintAssembly -XX:+PrintStubCode -Xloggc:./gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xms10M -Xmx10M -Xmn6m -XX:SurvivorRatio=8 -XX:+UseSerialGC gc.testNewArea
 * */

/**
 * # OopMapSet::all_do
 * (gdb) p *fr
 * $3 = {_sp = 0x7ffff7fda708, _pc = 0x7fffe11ffb2c "\220\220\220\220H\272\230\v`\377", _cb = 0x7fffe11ff310,
 *   _deopt_state = frame::not_deoptimized, static _check_value = {<OopClosure> = {<Closure> = {<StackObj> = {<AllocatedObj> = {
 *             _vptr.AllocatedObj = 0x7ffff6f01d90 <vtable for frame::CheckValueClosure+16>}, <No data fields>},
 *         _abort = false}, <No data fields>}, <No data fields>},
 *   static _check_oop = {<OopClosure> = {<Closure> = {<StackObj> = {<AllocatedObj> = {
 *             _vptr.AllocatedObj = 0x7ffff6f01d50 <vtable for frame::CheckOopClosure+16>}, <No data fields>},
 *         _abort = false}, <No data fields>}, <No data fields>},
 *   static _zap_dead = {<OopClosure> = {<Closure> = {<StackObj> = {<AllocatedObj> = {
 *             _vptr.AllocatedObj = 0x7ffff6f01d10 <vtable for frame::ZapDeadClosure+16>}, <No data fields>},
 *         _abort = false}, <No data fields>}, <No data fields>}, _fp = 0x7ffff7fda800, _unextended_sp = 0x7ffff7fda710}
 * (gdb) p *map
 * $4 = {<ResourceObj> = {<AllocatedObj> = {_vptr.AllocatedObj = 0x7ffff6f11890 <vtable for OopMap+16>}, _allocation_t = {
 *       18446603337294774726, 0}}, _pc_offset = 1324, _omv_count = 1, _omv_data_size = 2,
 *   _omv_data = 0x7fffbc0133d8 "\301d\361\361\361\361\361\361\220\030\361\366\377\177", _write_stream = 0x0,
 *   _locs_used = 0x7fffc0027ec8, _locs_length = 216}
 *
 * # frame::oopmapreg_to_location => 0x7ffff7fda768 = _unextended_sp + 88
 * (gdb) p *loc
 * $7 = (oop) 0xff719678
 * (gdb) p $7->klass()->_name->as_utf8()
 * $19 = 0x7ffff0030388 "demoObject/BigObject"
 *
 *   0x00007fffe11ffb14: addq   $0x1,0x188(%rbx)
 *   0x00007fffe11ffb1c: mov    %rax,%rsi          ;*invokespecial &lt;init&gt;
 *                                                 ; - gc.testNewArea::main@67 (line 28)
 *
 *   0x00007fffe11ffb1f: mov    %rax,0x58(%rsp)
 *   0x00007fffe11ffb24: nop
 *   0x00007fffe11ffb25: nop
 *   0x00007fffe11ffb26: nop
 *   0x00007fffe11ffb27: callq  0x00007fffe1105de0  ; OopMap{[88]=Oop off=1324}
 *                                                 ;*invokespecial &lt;init&gt;
 *                                                 ; - gc.testNewArea::main@67 (line 28)
 *                                                 ;   {optimized virtual_call}
 *   ;;  176 move [obj:0x0|L] [rdx|L] [patch_normal] [bci:71]
 *   0x00007fffe11ffb2c: nop
 *   0x00007fffe11ffb2d: nop
 *   0x00007fffe11ffb2e: nop
 *   0x00007fffe11ffb2f: nop
 *   0x00007fffe11ffb30: jmpq   0x00007fffe11fffac  ;   {no_reloc}
 * */