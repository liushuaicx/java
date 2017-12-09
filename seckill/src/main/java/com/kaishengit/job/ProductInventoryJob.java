package com.kaishengit.job;

import com.kaishengit.entity.Product;
import com.kaishengit.mapper.ProductMapper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author 刘帅
 */
public class ProductInventoryJob implements Job {

    private Logger logger = LoggerFactory.getLogger(ProductInventoryJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String productId = jobDataMap.getString("productId");

        try {
            //读取配置文件
            ApplicationContext applicationContext = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("springApplicationContext");
//------------------------------------------------------------------------------------------------------------------
            //读jedisPoll
            JedisPool jedisPool = (JedisPool) applicationContext.getBean("jedisPool");
            //获取redis中剩余的商品数量
            Jedis jedis = jedisPool.getResource();
            Long res = jedis.llen("product:"+productId+":num");

//------------------------------------------------------------------------------------------------------------------
            //读mapper文件
            ProductMapper productMapper = (ProductMapper) applicationContext.getBean("productMapper");
            //更新库存
            Product product = productMapper.selectByPrimaryKey(Integer.valueOf(productId));
            product.setProductInventory(res.intValue());
            productMapper.updateByPrimaryKey(product);


            logger.info("商品{}修改库存为{}",productId,res);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
