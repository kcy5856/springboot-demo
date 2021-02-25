package com.example.demo.mytest;

public class ThreadLocalTest implements Runnable{
    ThreadRunTest test = new ThreadRunTest();
    public static void main(String[] args){
        System.out.println(Thread.currentThread());
        new Thread(new ThreadLocalTest("aa")).start();
        new Thread(new ThreadLocalTest("bb")).start();

    }

    public ThreadLocalTest(String s){
        ThreadLocalContextHolder.set(s);
        System.out.println("==" + ThreadLocalContextHolder.get());
    }
    static int i = 1;

    @Override
    public void run() {
        ThreadLocalContextHolder.set("ccc"+ (i++)) ;
        System.out.println(Thread.currentThread() + "-----");
        test.get();
    }
}
