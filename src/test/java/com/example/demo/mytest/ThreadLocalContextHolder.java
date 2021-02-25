package com.example.demo.mytest;

public class ThreadLocalContextHolder {
    /**
     * 不同业务设置不同的业务场景，如：业务A设置值为1，业务B设置值为2...
     */
    private static ThreadLocal<Object> threadLocal = new ThreadLocal<>();


    public static Object get() {
        return threadLocal.get();
    }

    public static void set(Object scene) {
        if (ThreadLocalContextHolder.threadLocal == null) {
            ThreadLocalContextHolder.threadLocal = new ThreadLocal<>();
        }
        ThreadLocalContextHolder.threadLocal.set(scene);
    }

    public static void clear() {
        threadLocal.remove();
    }

}
