package com.example.demo.utils;

/**
 * 线程参数共享：
 * 1. 开启线程时手动设置(set)后，线程内所有皆可直接获取(get)
 * 2. 如果使用线程池，结束时需手工清理(clear)
 */
public class ThreadLocalContextHolder {

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
