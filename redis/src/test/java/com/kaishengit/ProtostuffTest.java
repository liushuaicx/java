package com.kaishengit;

import com.kaishengit.pojo.User;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ProtostuffTest {

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void save() {
        User user = new User(1,"jack","UK");

        Jedis jedis = jedisPool.getResource();
        //将对象序列化为字节数组
        Schema<User> schema = RuntimeSchema.getSchema(User.class);
        byte[] bytes = ProtobufIOUtil.toByteArray(user,schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        jedis.set("user:2".getBytes(),bytes);
        jedis.close();

    }

    @Test
    public void get() {

        Jedis jedis = jedisPool.getResource();
        byte[] bytes = jedis.get("user:2".getBytes());
        Schema<User> schema = RuntimeSchema.getSchema(User.class);
        User user = new User();
        ProtobufIOUtil.mergeFrom(bytes,user,schema);
        System.out.println(user);
        jedis.close();
    }
}
