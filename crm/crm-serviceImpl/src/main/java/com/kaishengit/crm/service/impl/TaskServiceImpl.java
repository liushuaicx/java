package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.example.TaskExample;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.jobs.SendMessageJob;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.mapper.TaskMapper;
import com.kaishengit.crm.mapper.UserMapper;
import com.kaishengit.crm.service.TaskService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author 刘帅
 */
@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    /**
     * 无条件查询
     *
     * @param id userId
     * @return
     */
    @Override
    public List<Task> findTaskByUserId(Integer id) {

        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria().andUserIdEqualTo(id);
        taskExample.setOrderByClause("id desc");

        return taskMapper.selectByExample(taskExample);
    }

    /**
     * @param id userId
     * @return
     */
    @Override
    public List<Task> findTaskWithCustNameByUserId(Integer id) {

        List<Task> taskList = taskMapper.findTaskWithCustNameByUserId(id);
        return taskList;
    }

    /**
     * 查询带SaleName
     *
     * @param id userId
     * @return
     */
    @Override
    public List<Task> findTaskWithSaleNameByUserId(Integer id) {

        List<Task> taskList = taskMapper.findTaskWithSaleNameByUserId(id);
        return taskList;
    }

    /**
     * 通过id查找Task对象
     *
     * @param id
     * @return
     */
    @Override
    public Task findById(Integer id) {

        return taskMapper.selectByPrimaryKey(id);
    }

    /**
     * 更改状态为done
     *
     * @param id
     * @param done
     */
    @Override
    public void updateStates(Integer id, Byte done) {

        Task task = findById(id);
        Byte b = 1;
        task.setDone(b);
        taskMapper.updateByPrimaryKey(task);

    }

    /**
     * 更改状态为UnDone
     * @param id
     * @param done
     */
    @Override
    public void updateStatesToUnDone(Integer id, Byte done) {

        Task task = findById(id);
        task.setDone((byte) 0);
        taskMapper.updateByPrimaryKey(task);
    }

    /**
     * 新增代办事项
     * @param task
     */
    @Override
    public void newTask(Task task) {


        if (task.getCustId() != 0) {
            Customer customer = customerMapper.selectByPrimaryKey(task.getCustId());
            task.setDone((byte) 0);
            task.setCreateTime(new Date());
            task.setTitle(task.getTitle()+"<a href=\"/customer/detail/"+ task.getCustId() +"\">"+ customer.getCustName() +"</a>");

            taskMapper.insert(task);
        }else {
            task.setCustId(null);
            task.setDone((byte) 0);
            task.setCreateTime(new Date());
            taskMapper.insert(task);
        }


        //添加新的调度任务
        if (StringUtils.isNotEmpty(task.getRemindTime())) {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.putAsString("userId", task.getUserId());
            jobDataMap.put("message", task.getTitle());

            //创建任务
            JobDetail jobDetail = JobBuilder.newJob(SendMessageJob.class)
                    .setJobData(jobDataMap)
                    .withIdentity(new JobKey("taskID:" + task.getId(), "sendMessageGroup")).build();

            //String转DateTime类型
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
            DateTime dateTime = formatter.parseDateTime(task.getRemindTime());

            //2017-09-08 12:35 -> cron
            StringBuilder cron = new StringBuilder("0").append(" ").append(dateTime.getMinuteOfHour())
                    .append(" ").append(dateTime.getHourOfDay())
                    .append(" ").append(dateTime.getDayOfMonth())
                    .append(" ").append(dateTime.getMonthOfYear())
                    .append(" ? ").append(dateTime.getYear());


            ScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron.toString());

            //创建触发器
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();

            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            try {
                scheduler.scheduleJob(jobDetail, trigger);
                scheduler.start();

            } catch (SchedulerException e) {

                throw new ServiceException("添加定时任务异常");
            }
        }

    }

    /**
     * 删除待办事项
     *
     * @param id
     */
    @Override
    public void taskDel(Integer id) {

        Task task = findById(id);
        taskMapper.deleteByPrimaryKey(id);

        //删除定时任务
        if (StringUtils.isNotEmpty(task.getRemindTime())) {
            //创建Scheduler 对象
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.deleteJob(new JobKey("taskID:" + id, "sendMessageGroup"));
                logger.info("成功删除定时任务,ID:{},groupName:{}", id, "sendMessageGroup");
            } catch (SchedulerException e) {
                throw new ServiceException(e, "删除定时任务异常");
            }
        }

    }

    /**
     * 查询未完成列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Task> findTaskByUserIdAndUnDone(Integer userId) {

        return taskMapper.findTaskByUserIdAndUnDone(userId);
    }
}
