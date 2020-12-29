package com.example.demo.mytest;

public class ThreadLocalContextHolder {
    /**
     * 不同业务设置不同的业务场景，如：业务A设置值为1，业务B设置值为2...
     */
    private static ThreadLocal<String> sceneThreadLocal = new ThreadLocal<>();


    public static String getScene() {
        return sceneThreadLocal.get();
    }

    public static void initScene(String scene) {
        if (ThreadLocalContextHolder.sceneThreadLocal == null) {
            ThreadLocalContextHolder.sceneThreadLocal = new ThreadLocal<>();
        }
        ThreadLocalContextHolder.sceneThreadLocal.set(scene);
    }

    public static void clearScene() {
        initScene(null);
    }

}
