package com.kaishengit;

import com.kaishengit.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpringDataRedisTest {

    private RedisTemplate<String,User> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, User> redisTemplate) {
        this.redisTemplate = redisTemplate;
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
    }

    @Test
    public void save() {
        User user = new User(2,"tom","河南");
        redisTemplate.opsForValue().set("user:1",user);
    }



    /* @Test
    public void save() {
        redisTemplate.opsForValue().set("name","jack");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }*/

}
