package demo;

/**
 * Created by johnny.ly on 2016/8/15.
 */
public class testTryFinally {
    public static void main(String[] args) {
        System.out.println(test());
    }

    public static int test(){
        try {
            return test1();
        } catch (Exception e) {
            System.out.println("exc called");
            return 10;
        } finally {
            System.out.println("finally called");
            return 100;
        }
    }

    public static int test1(){
        System.out.println("test1 called");
        return 10;
    }
}

/*
1.try语句块，return test1()，则调用test1方法
2.test1()执行后返回"after return"，返回值"after return"保存在一个临时区域里
3.执行finally语句块。若finally语句有返回值，则此返回值将替换掉临时区域的返回值，异常也会被吃掉!!! (对比一下finally有return和没return的字节码)
4.将临时区域的返回值送到上一级方法中。
5.try语句没有被执行的话，finally也是不会被执行的
6.finally和synchronized一样生成"any type"类型的异常表项，匹配任何类型的异常，finally针对try和catch重复生成对应的字节码
*/