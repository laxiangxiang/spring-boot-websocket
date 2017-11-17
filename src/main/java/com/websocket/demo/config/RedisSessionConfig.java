package com.websocket.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 这时候登录系统在不同的app之间跳转的时候，session都是一致了，redis上可以看到：
 * 如果需要添加失效时间可以使用以下的写法：
 @EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60) //1分钟失效
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {
}
