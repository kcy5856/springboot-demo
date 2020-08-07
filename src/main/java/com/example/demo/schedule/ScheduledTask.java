package com.example.demo.schedule;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.example.demo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	RedisUtils redisUtils;
	
	@Scheduled(cron = "${task.print}")
	public void printWorld() {
		System.out.println("task printWorld() executed ok");
	}

	@Async
	@Scheduled(fixedRate = 60000, initialDelay = 12000)
	public void reportMyNewCurrentTime() {
	    System.out.println(Thread.currentThread() +"BBB每隔60秒钟执行一次： " + new Date());
	    
	}

	@Async
	@Scheduled(cron = "0 0/10 * * * ?")
	public void fixTimeExecution() {
		boolean lock = redisUtils.getLock("targetTime", 600, TimeUnit.SECONDS);
		if (lock) {
			System.out.println("在指定时间 " + new Date() + "执行");
		}
	}
	
}
