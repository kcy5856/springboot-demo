package com.example.demo.schedule;

import java.util.Date;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务统一处理
 * @author harmonyCloud
 *
 */
@Component
@EnableAsync
public class ScheduledTask {
	
	@Scheduled(cron = "${task.print}")
	public void printWorld() {
		System.out.println("task printWorld() executed ok");
	}
	
	
	@Async
	@Scheduled(fixedRate = 10000, initialDelay = 10000)
	public void reportMyOldCurrentTime() {
	    System.out.println(Thread.currentThread() + "AAA每隔50钟执行一次： " + new Date());
	}

	@Async
	@Scheduled(fixedRate = 60000, initialDelay = 12000)
	public void reportMyNewCurrentTime() {
	    System.out.println(Thread.currentThread() +"BBB每隔60秒钟执行一次： " + new Date());
	    
	}

	@Async
	@Scheduled(cron = "0 48 16 ? * *")
	public void fixTimeExecution() {
	    System.out.println("在指定时间 " + new Date() + "执行");
	}
	
}
