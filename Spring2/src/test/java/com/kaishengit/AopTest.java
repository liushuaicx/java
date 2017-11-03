package com.kaishengit;

import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
public class AopTest {
    @Autowired
    private UserService userService;

    @Test
    public void Test() throws Exception {
        User user = new User();
        user.setName("tom");
        user.setAddress("UK");
        userService.save(user);
    }
}
