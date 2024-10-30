package com.urbantransport.geolocalisation_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/*
This class provides a thread-safe bridge between Spring and Redis Commands.
It handles connection management, freeing the developer from opening and closing Redis connections.
It also provides serialization and deserialization of objects, which is useful when storing objects in Redis.
*/

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
