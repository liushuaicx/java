package com.kaishengit;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTest {

    @Test
    public void save() {

        Jedis jedis = new Jedis("192.168.208.30",6379);
        jedis.set("name","tom");
        jedis.close();

    }
    @Test
    public void get() {
        Jedis jedis = new Jedis("192.168.208.30",6379);
        String name = jedis.get("name");
        System.out.println(name);
        jedis.close();
    }

    @Test
    public void genericSave() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(8);
        config.setMinIdle(5);
        JedisPool jedisPool = new JedisPool(config,"192.168.208.30",6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("age","11");
        jedis.close();
        jedisPool.destroy();

    }

}
