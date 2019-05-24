package com.dynamicInvoke;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/5/24
 * Time: 10:19
 * Desc:
 */
public class MethodReference {
    public static void main(String[] args) {
        Encode encode = Base::encrypt; // ???
        System.out.println(encode);
        encode.encode(new Base());// Base::speak
        encode.encode(new Derive());// Derive::speak
    }
}

interface Encode {
    void encode(Base person);
}

class Base {
    public void encrypt() {
        System.out.println("Base::speak");
    }
}

class Derive extends Base {
    @Override
    public void encrypt() {
        System.out.println("Derive::speak");
    }
}


