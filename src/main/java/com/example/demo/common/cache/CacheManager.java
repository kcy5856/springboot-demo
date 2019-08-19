package com.example.demo.common.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class CacheManager {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private final long expireTime = 60000000;
	
	public void set(String key, Object value) {
		set(key, value, expireTime);
	}
	
	public void set(String key, Object value, long time) {
		redisTemplate.boundValueOps(key).set(value);
		redisTemplate.boundValueOps(key).expire(expireTime, TimeUnit.MILLISECONDS);
	}
	
	public void expire(String key, long time) {
		redisTemplate.boundValueOps(key).expire(expireTime, TimeUnit.MILLISECONDS);
	}
	
	public Object get(String key) {
		return redisTemplate.boundValueOps(key).get();
	}
	
}
