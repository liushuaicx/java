package com.kaishengit;

import com.kaishengit.dao.UserDao;
import com.kaishengit.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTestCase {

    @Test
    public void getBean() {

        //获取Spring容器
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //从Spring容器中获得Bean
//        UserDao userDao = (UserDao) context.getBean("myUserDao");
//        UserDao userDao2 = (UserDao) context.getBean("myUserDao");

//        System.out.println(userDao == userDao2);
//        userDao.Hello();
        UserServiceImpl userService = (UserServiceImpl)context.getBean("userService");
        userService.save();
    }
}
