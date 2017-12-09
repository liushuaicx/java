package com.kaishengit.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.kaishengit.entity.Product;
import com.kaishengit.entity.ProductExample;
import com.kaishengit.job.ProductInventoryJob;
import com.kaishengit.mapper.ProductMapper;
import com.kaishengit.service.ProductService;
import com.kaishengit.util.ServiceException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author 刘帅
 */
@Service
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;
    @Value("${qiniu.ak}")
    private String qiniuAk;
    @Value("${qiniu.sk}")
    private String qiniuSk;
    @Value("${qiniu.buket}")
    private String qiniuBuket;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveProduct(Product product, InputStream inputStream) {
        //上传到七牛云
        Configuration configuration = new Configuration(Zone.zone1());
        UploadManager uploadManager = new UploadManager(configuration);

        Auth auth = Auth.create(qiniuAk,qiniuSk);
        String uploadToken = auth.uploadToken(qiniuBuket);

        String key = null;
        try {
            Response response = uploadManager.put(IOUtils.toByteArray(inputStream),null,uploadToken);
            DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
            key = defaultPutRet.key;

        } catch (IOException e) {
            throw new RuntimeException("上传到七牛云异常");
        }

        //保存product对象
        product.setProductImage(key);
        productMapper.insert(product);

        //在redis中添加商品库存量集合lpush,在秒杀时抛出lpop
        try(Jedis jedis = jedisPool.getResource()) {
            for (int i = 0 ; i < product.getProductInventory(); i++) {
                jedis.lpush("product:"+product.getId()+":num",String.valueOf(i));
            }
        }
        //添加秒杀结束的定时任务,用于秒杀结束时更新库存

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAsString("productId",product.getId());

        JobDetail jobDetail = JobBuilder.newJob(ProductInventoryJob.class).setJobData(jobDataMap)
                .withIdentity(new JobKey("taskId"+product.getId(),"productInventoryGroup")).build();

        DateTime dateTime = new DateTime(product.getEndTime());

        StringBuilder cron = new StringBuilder("0")
                .append(" ")
                .append(dateTime.getMinuteOfHour())
                .append(" ")
                .append(dateTime.getHourOfDay())
                .append(" ")
                .append(dateTime.getDayOfMonth())
                .append(" ")
                .append(dateTime.getMonthOfYear())
                .append(" ? ")
                .append(dateTime.getYear());

        ScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron.toString());
        //创建触发器
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();
        //创建调度器
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        try {
            scheduler.scheduleJob(jobDetail,trigger);
        } catch (SchedulerException e) {
            throw new ServiceException("创建定时任务异常");
        }
    }

    /**
     * 查询商品列表
     *
     * @return
     */
    @Override
    public List<Product> findAll() {
        ProductExample productExample = new ProductExample();
        return productMapper.selectByExample(productExample);
    }

    /**
     * 根据id查找商品
     * @param id
     * @return
     */
    @Override
    public Product findById(Integer id) {
        Product product;
        //无论是否执行完成都会关闭jedis
        try(Jedis jedis = jedisPool.getResource()) {
            String productDetail = jedis.get("product:"+id);
            if (productDetail == null) {
                product = productMapper.selectByPrimaryKey(id);
                jedis.set("product:"+id, JSON.toJSONString(product));
            }else {
                product = JSON.parseObject(productDetail,Product.class);
            }
        }
        return product;
    }

    /**
     * 秒杀
     * @param id
     */
    @Override
    public void secKill(Integer id) {

        try(Jedis jedis = jedisPool.getResource()) {
            Product product = JSON.parseObject(jedis.get("product:"+id),Product.class);
            //每执行一次抛出一个
            String res = jedis.lpop("product:"+id+":num");
            if (res == null) {
                logger.error("抢光了,库存不足");
                throw new ServiceException("抢光了");
            }else {
                logger.info("秒杀成功");
                //修改redis的缓存
                product.setProductInventory(product.getProductInventory() - 1);
                jedis.set("product:"+id,JSON.toJSONString(product));
            }
        }
    }
}
