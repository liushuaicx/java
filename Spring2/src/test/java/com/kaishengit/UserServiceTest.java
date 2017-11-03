package com.kaishengit;

import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")/*(classes = ApplicationContext.class)*/
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Test
    public void save() throws Exception {

        User user = new User();
        user.setName("tom");
        user.setAddress("UK");
        userService.save(user);
    }
}

