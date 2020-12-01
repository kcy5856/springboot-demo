package com.example.demo.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 线程池
 * @author harmonyCloud
 *
 */
public class ThreadPool {
	
	private volatile static ThreadPoolExecutor executor = null;
	
	private ThreadPool() {}
	
	public static void execute(Runnable run) {
		if (executor == null) {
			synchronized(ThreadPool.class) {
				if (executor == null) {
					ThreadPool factory = new ThreadPool();
					factory.init();
				}
			}
		}
		executor.execute(run);;
	}
	
	
	public void init() {
		ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("common-pool-%d").build();
		int initSize = 10;		//可改为配置
		int maxSize = 20;
		long aliveTime = 0L;
		TimeUnit unit = TimeUnit.MILLISECONDS;
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(8);
		executor = new ThreadPoolExecutor(initSize, maxSize, aliveTime, unit, workQueue, threadFactory, new ThreadPoolExecutor.AbortPolicy());
	}
}
