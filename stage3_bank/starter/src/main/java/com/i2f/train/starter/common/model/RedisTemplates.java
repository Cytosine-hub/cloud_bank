package com.i2f.train.starter.common.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/02/27/20:05
 * @Description:
 */
@Component
public class RedisTemplates {

    private final static long EXPIRE_Time = 24 * 7;

    @Autowired
    @Qualifier("redisTemplate")
    private static RedisTemplate<String, Object> redisTemplate = redisTemplate(redisConnectionFactory());


    public static String generateToken(String userId, String token){
        redisTemplate.opsForValue().set(token, userId, 1, TimeUnit.DAYS);
        return token;
    }

    public static String getUserId(String token){
//            String UserId = JwtUtils.checkToken(token);
        String userId = (String) redisTemplate.opsForValue().get(token);
        if(null != userId){
            return userId;
        } else {
            return null;
        }
    }

    public static void delete(String token){
        redisTemplate.delete(token);
    }

    public static void main(String[] args) {
//            RedisToken redisToken = new RedisToken();
//            String token = redisToken.generateToken("002");
//            System.out.println(token);
//            System.out.println(redisToken.getCode(token));
    }



    private static RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("81.69.249.115");
        redisStandaloneConfiguration.setPort(56379);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        jedisConnectionFactory.afterPropertiesSet();
//        return new JedisConnectionFactory(redisStandaloneConfiguration);
        return jedisConnectionFactory;
    }

    private static RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}

