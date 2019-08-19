package com.example.demo.schedule;

import java.util.Date;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.thread.ThreadPool;

/**
 * 定时任务统一处理
 * @author harmonyCloud
 *
 */
@Component
@EnableScheduling
@EnableAsync
public class ScheduledTask {
	
	@Async
	@Scheduled(fixedRate = 500000, initialDelay = 12000)
	public void reportCurrentTime() {
	    System.out.println(Thread.currentThread() + "每隔五秒钟执行一次： " + new Date());
	}

	@Async
	@Scheduled(fixedRate = 500000, initialDelay = 12000)
	public void reportNewCurrentTime() {
	    System.out.println(Thread.currentThread() +"每隔新五秒钟执行一次： " + new Date());
	    
	}

	@Async
	@Scheduled(cron = "0 48 16 ? * *")
	public void fixTimeExecution() {
	    System.out.println("在指定时间 " + new Date() + "执行");
	}
	
}
