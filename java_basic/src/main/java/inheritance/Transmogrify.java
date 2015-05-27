/*
首先考虑composition，再考虑inheritance，组合更加灵活，它可以动态选择类型，
继承会使事物结构复杂起来!
一条通用的准则是：“用继承表达行为间的差异，并用属性表达状态上的变化”。在上述例
子中，两者都用到了：通过继承得到了两个不同的类，用于表达act()方法的差异；而Stage
通过运用组合使它自己的状态发生变化。在这种情况下，这种状态的改变也就产生了行为的
改变。
 */
package inheritance;

abstract class Actor {

    public abstract void act();
}

class HappyActor extends Actor {

    public void act() {
        System.out.println("HappyActor");
    }
}

class SadActor extends Actor {

    public void act() {
        System.out.println("SadActor");
    }
}

class Stage {

    private Actor actor = new HappyActor();

    public void change() {
        actor = new SadActor();
    }

    public void performPlay() {
        actor.act();
    }
}

public class Transmogrify {

    public static void main(String[] args) {
        Stage stage = new Stage();
        stage.performPlay();
        stage.change();
        stage.performPlay();

    }
}
