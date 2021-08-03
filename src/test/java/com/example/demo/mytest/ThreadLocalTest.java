package com.example.demo.mytest;

public class ThreadLocalTest {

    public static void main(String[] args){
    	ThreadLocalContextHolder.set("main");
    	
    	Thread threadFirst = new Thread(()->{
    		WriteSome write = new WriteSome();
        	String writeName = write.writeName("first");
        	System.out.println(writeName);
        	ReadSome read = new ReadSome();
        	String readName = read.getReadName();
        	System.out.println(readName);
    	});
    	
    	Thread threadSecond = new Thread(()->{
    		WriteSome write = new WriteSome();
        	String writeName = write.writeName("second");
        	System.out.println(writeName);
        	ReadSome read = new ReadSome();
        	String readName = read.getReadName();
        	System.out.println(readName);
    	});
    	
    	threadFirst.start();
    	threadSecond.start();
    	
    	String main = (String) ThreadLocalContextHolder.get();
    	System.out.println(main);
    	
    }

    
}

class ReadSome {
	
	public String getReadName(){
		String name = (String) ThreadLocalContextHolder.get();
		return "read " + name;
	}
}

class WriteSome {

	
	public String writeName(String name){
		ThreadLocalContextHolder.set(name);
		return "write " + name;
	}
}
