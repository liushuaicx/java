package com.kaishengit;

import com.alibaba.fastjson.JSON;
import com.kaishengit.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpringJedisTest {

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void save() {
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.get("sex"));;
        jedis.close();
    }

    @Test
    public void savePojo() {
        Jedis jedis = jedisPool.getResource();
        User user = new User(1,"jack","南京");
        jedis.set("user", JSON.toJSONString(user));
        jedis.close();
    }

    @Test
    public void getPojo() {
        Jedis jedis = jedisPool.getResource();
        String user = jedis.get("user");
        User user1 = JSON.parseObject(user,User.class);
        System.out.println(user1);
    }
}
