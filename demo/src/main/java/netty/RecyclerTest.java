package netty;

import io.netty.util.Recycler;

/**
 * Created by IntelliJ IDEA.
 * User: win10
 * Date: 2019/5/18
 * Time: 22:37
 * Desc:  Recycler就是轻量级的对象池，反复使用对象避免频繁申请和回收内存
 */
public class RecyclerTest {
    public static void main(String args[]) throws Exception{
        Recycler<Test> recycler = new Recycler<Test>() {
            @Override
            protected Test newObject(Handle handle) {
                System.out.println("create new object");
                return new Test(handle);
            }
        };
        Test tet = recycler.get();
        tet.print();
        tet.setName("test1");
        tet.print();
        recycler.recycle(tet, tet.getHandle());

        tet = recycler.get();
        tet.print();
        tet.setName("test2");
        tet.print();
    }

    public static class Test{
        private Recycler.Handle handle;
        private String name;

        public Test(Recycler.Handle handle) {
            this.handle = handle;
        }

        public Recycler.Handle getHandle() {
            return handle;
        }

        public void setHandle(Recycler.Handle handle) {
            this.handle = handle;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void print(){
            System.out.println("name " + name);
        }
    }
}