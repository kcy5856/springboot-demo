package com.example.demo.mytest;

import org.apache.poi.ss.formula.functions.T;

public class ParamTest {
    ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args){
        ParamTest paramTest = new ParamTest();
        paramTest.threadLocal.set("stra");
        System.out.println("main: " + paramTest.threadLocal.get());
        System.out.println("1:" + Thread.currentThread());
        String s = paramTest.threadLocal.get();
        new Thread(()->{
            System.out.println("2:" + Thread.currentThread());
            ThreadLocalContextHolder.set(s);
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            A a = new A();
            a.getName();
        }).start();
        System.out.println("10:" + Thread.currentThread());
    }


}


class A  {

    public void getName(){
        System.out.println("5:" + Thread.currentThread());
        System.out.println("getName: " + ThreadLocalContextHolder.get());
    }


}
