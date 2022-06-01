package com.lsy.test.system.common.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * @author 黄雪冬
 * @version 0.0.1
 * @date 2019/8/24 16:22
 */
@Configuration
@EnableCaching
public class RedisConfig {


    @Bean
    public RedisSerializer fastJson2JsonRedisSerializer() {
        return new FastJsonRedisSerializer();
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        Jackson2JsonRedisSerializer valueRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //设置Redis的value为json格式,并存储对象信息的序列化类型
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        valueRedisSerializer.setObjectMapper(objectMapper);

        RedisSerializer keyRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(keyRedisSerializer);
        template.setValueSerializer(valueRedisSerializer);
        template.setHashKeySerializer(keyRedisSerializer);
        template.setHashValueSerializer(valueRedisSerializer);
        template.setConnectionFactory(factory);
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, "");
    }
}
