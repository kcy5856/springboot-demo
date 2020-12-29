package com.example.demo.utils;

public class ThreadLocalContextHolder {
    /**
     * 不同业务设置不同的业务场景，如：业务A设置值为1，业务B设置值为2...
     */
    private static ThreadLocal<String> local = new ThreadLocal<>();


    public static String get() {
        return local.get();
    }

    public static void set(String scene) {
        if (ThreadLocalContextHolder.local == null) {
            ThreadLocalContextHolder.local = new ThreadLocal<>();
        }
        ThreadLocalContextHolder.local.set(scene);
    }

    public static void clearScene() {
        ThreadLocalContextHolder.local.remove();
    }

}
