package com.example.demo.aspect;

import com.example.demo.annotation.Limit;
import com.example.demo.common.enums.LimitType;
import com.example.demo.exception.BizException;
import com.example.demo.utils.RequestHolder;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;


@Component
@Aspect
public class LimitAspect {
	private final RedisTemplate<Object,Object> redisTemplate;
	private static final Logger logger = LoggerFactory.getLogger(LimitAspect.class);

	public LimitAspect(RedisTemplate<Object,Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Pointcut("@annotation(com.example.demo.annotation.Limit)")
	public void pointcut() {
	}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = RequestHolder.getHttpServletRequest();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method signatureMethod = signature.getMethod();
		Limit limit = signatureMethod.getAnnotation(Limit.class);
		LimitType limitType = limit.limitType();
		String key = limit.key();
		if (StringUtils.isEmpty(key)) {
			if (limitType == LimitType.IP) {
				key = getIp(request);
			} else {
				key = signatureMethod.getName();
			}
		}

		ImmutableList<Object> keys = ImmutableList.of(StringUtils.join(limit.prefix(), "_", key, "_", request.getRequestURI().replaceAll("/","_")));

		String luaScript = buildLuaScript();
		RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
		Number count = redisTemplate.execute(redisScript, keys, limit.count(), limit.period());
		if (null != count && count.intValue() <= limit.count()) {
			logger.info("第{}次访问key为 {}，描述为 [{}] 的接口", count, keys, limit.name());
			return joinPoint.proceed();
		} else {
			throw new BizException("访问次数受限制");

		}
	}

	/**
	 * 限流脚本
	 */
	private String buildLuaScript() {
		return "local c" +
				"\nc = redis.call('get',KEYS[1])" +
				"\nif c and tonumber(c) > tonumber(ARGV[1]) then" +
				"\nreturn c;" +
				"\nend" +
				"\nc = redis.call('incr',KEYS[1])" +
				"\nif tonumber(c) == 1 then" +
				"\nredis.call('expire',KEYS[1],ARGV[2])" +
				"\nend" +
				"\nreturn c;";
	}

	private static final String UNKNOWN = "unknown";
	/**
	 * 获取ip地址
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		String comma = ",";
		String localhost = "127.0.0.1";
		if (ip.contains(comma)) {
			ip = ip.split(",")[0];
		}
		if (localhost.equals(ip)) {
			// 获取本机真正的ip地址
			try {
				ip = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return ip;
	}

}
