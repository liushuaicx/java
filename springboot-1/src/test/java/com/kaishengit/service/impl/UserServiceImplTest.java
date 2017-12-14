package com.kaishengit.service.impl;

import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void save() {

        User user = new User();
        user.setUserName("tom");
        user.setAddress("UK");
        userService.save(user);
    }
}