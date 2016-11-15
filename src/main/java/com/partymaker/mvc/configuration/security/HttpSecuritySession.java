package com.partymaker.mvc.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

import java.io.IOException;

/**
 * Created by anton on 10/10/16.
 */
@Configuration
@EnableRedisHttpSession // add servlet filter that replay HttpSession to SpringSession
public class HttpSecuritySession {

    /* integration to use HTTP headers */
    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

    /* connects Spring Session to the Redis Server */
    @Bean
    public RedisConnectionFactory connectionFactory() throws IOException {
        RedisConnectionFactory factory = new JedisConnectionFactory();
        return factory;
    }

    @Autowired
    public SessionRepository sessionRepository(RedisConnectionFactory connectionFactory) {
        RedisOperationsSessionRepository redisOperationsSessionRepository =
                new RedisOperationsSessionRepository(connectionFactory);
        redisOperationsSessionRepository.setDefaultMaxInactiveInterval(2000000000);
        redisOperationsSessionRepository.createSession();
        redisOperationsSessionRepository.setRedisKeyNamespace("savelife");

        return redisOperationsSessionRepository;
    }
}
