package com.kaishengit.crm.jobs;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 *
 * @author 刘帅
 */
public class SendMessageJob implements Job {

    private Logger logger = LoggerFactory.getLogger(SendMessageJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String message = (String) jobDataMap.get("message");
        Integer userId = jobDataMap.getInt("userId");

        logger.info("To:{} Message:{}",userId,message);

        try {
            ApplicationContext applicationContext = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("springApplicationContext");
            JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
            jmsTemplate.send("WeiXinMessageQueue", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {

                    String json = "{\"id\":\"LiuShuai\",\"message\":\"Hello,Message from JMS\"}";
                    return session.createTextMessage(json);
                }
            });

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
