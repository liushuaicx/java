package com.kaishengit.crm.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author 刘帅
 */
public class MyQuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //取值
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Integer userId = dataMap.getInt("userId");

        System.out.println("Quartz Running..." + userId);
    }
}
