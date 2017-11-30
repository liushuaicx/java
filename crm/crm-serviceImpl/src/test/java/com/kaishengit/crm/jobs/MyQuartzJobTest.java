package com.kaishengit.crm.jobs;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;

public class MyQuartzJobTest {

    @Test
    public void simpleTrigger() throws SchedulerException, IOException {

        //定义job
        JobDetail jobDetail = JobBuilder.newJob(MyQuartzJob.class).build();
        //定义Trigger
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        scheduleBuilder.withIntervalInSeconds(2);
        scheduleBuilder.repeatForever();
        Trigger simpleTrigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.scheduleJob(jobDetail,simpleTrigger);
        scheduler.start();

        System.in.read();


    }
}
