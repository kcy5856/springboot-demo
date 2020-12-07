package com.example.demo.mytest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;

public class Test6 {
    private static final Logger log = LoggerFactory.getLogger(Test6.class);

    public static void main(String[] args){
        String[] test = {"a", "b", "c"};
        System.out.println(test);
        log.info("test", test);

        //获取springboot根目录
        ApplicationHome ah = new ApplicationHome(Test6.class);
        String docStorePath = ah.getSource().getParentFile().toString();
        System.out.println(docStorePath);
        System.out.println(Test6.class.getResourceAsStream("/"));
    }
}
