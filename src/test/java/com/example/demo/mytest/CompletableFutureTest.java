package com.example.demo.mytest;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {
    public static void main(String[] args){
        List<String> bos = new ArrayList<>();
        bos.addAll(Arrays.asList("a=1","b=2","c=3","d=4","e=5"));

        CompletableFutureTest test = new CompletableFutureTest();
        //test.testAllOf(bos);
        test.testAnyOf(test);
    }

    private void testAnyOf(CompletableFutureTest test){
        CompletableFuture<Bo> str1 = CompletableFuture.supplyAsync(()->{
            Bo bo = test.new Bo();
            bo.setKey("aa");
            bo.setName("11");
            return bo;
        });
        CompletableFuture<Bo> str2 = CompletableFuture.supplyAsync(()->{
            Bo bo = test.new Bo();
            bo.setKey("bb");
            bo.setName("22");
            return bo;
        });
        System.out.println("----------");
        Bo bo = (Bo) CompletableFuture.anyOf(str1, str2).join();
        System.out.println(bo.getClass());
        System.out.println("sssss==" + JSON.toJSONString(bo));

    }

    private void testAllOf(List<String> bos){
        List<CompletableFuture<Bo>> futureList = new ArrayList<>();
        CompletableFutureTest test = new CompletableFutureTest();
        System.out.println(JSON.toJSONString(bos));

        for (String value: bos){
            CompletableFuture<Bo> thebo = CompletableFuture.supplyAsync(() -> {
                Bo bo = test.new Bo();
                String[] split = value.split("=");
                bo.setKey(split[1]);
                bo.setName(split[0]);
                System.out.println(value);
                return bo;
            });
            futureList.add(thebo);
        }

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));

        System.out.println("aaaaaaaaaaa");

        voidCompletableFuture.join();
        List<Bo> boList = new ArrayList<>();
        for (CompletableFuture<Bo> arg: futureList){
            Bo bo = arg.join();
            boList.add(bo);
        }
        System.out.println(JSON.toJSONString(boList));
    }

    class Bo {
        private int id;
        private String name;
        private String key;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
