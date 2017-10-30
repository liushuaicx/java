package com.kaishengit;

import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")

public class UserDaoTest {

    @Autowired
    private UserDao userDao;
    @Test
    public void saveTest() {

        User user = new User();
        user.setName("rose");
        user.setAddress("TW");
        userDao.save(user);
    }

    @Test
    public void findById() {

        User user = userDao.findById(1);

        System.out.println(user);
    }
    @Test
    public void findAll() {

        List<User> userList = userDao.findAll();

        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void count() {

        int count = userDao.count();
        System.out.println(count);
    }



}
