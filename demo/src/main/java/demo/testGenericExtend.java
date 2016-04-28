package demo;

/**
 * Created by johnny.ly on 2016/4/27.
 */
public class testGenericExtend {
    public static void main(String[] args){
        concret c = new concret();
        c.openAgain(new Object());//return Object;
        c.open(111);//open(Obejct)

        concret<String> con = new concret<String>();
        con.openAgain("test");//return string;
        con.open(111);//open(Obejct)


        concrete ce = new concrete();
        ce.openAgain(new Object());//return Object
        ce.open(new Object());//strange !!!

        concrete<Double> cone = new concrete<Double>();
        cone.openAgain(34.53D);//return double
        cone.open("test");//open(String)
    }
}

class generic<T>{
    private T ele;

    public void open(T e){

    }
}

class concret<E> extends generic{//open(Object)

    public E openAgain(E e){
        E el = null;
        return el;
    }
}

class concrete<E> extends generic<String>{
    public E openAgain(E e){
        E el = null;
        return el;
    }
}
