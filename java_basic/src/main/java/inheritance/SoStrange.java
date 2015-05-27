/*
1. 在其他任何事物发生之前，将分配给对象的存储空间初始化成二进制的零。
2. 如前所述的那样，调用基类构造器。此时，调用被重载的draw()方法（是的，是
在调用RoundGlyph 构造器之前调用的），由于步骤(1)的缘故，我们此时会发
现radius 的值为0。
3. 按照声明的顺序调用成员的初始化代码。
4. 调用导出类的构造器主体。
 */

//dont need to review again
package inheritance;

abstract class Glyph {

    abstract void draw();

    Glyph() {
        System.out.println("Glyph() before draw()");
        draw();
        System.out.println("Glyph() after draw()");
    }
}

class RoundGlyph extends Glyph {

    private int radius = 1;

    RoundGlyph(int r) {
         System.out.println(
                "RoundGlyph.RoundGlyph(), radius = " + radius);
        radius = r;
        System.out.println(
                "RoundGlyph.RoundGlyph(), radius = " + radius);
    }

    void draw() {
        System.out.println(
                "RoundGlyph.draw(), radius = " + radius);
    }
}

public class SoStrange {

    public static void main(String[] args) {
        new RoundGlyph(5);

    }
}
