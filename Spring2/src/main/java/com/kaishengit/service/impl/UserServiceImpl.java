package com.kaishengit.service.impl;

import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(User user) {

        userDao.save(user);
        if(true) {
            try {
                throw new Exception("手动抛出异常测试");
            } catch (Exception e) {
                throw new RuntimeException("手动修改异常");
            }
        }
        userDao.save(user);
    }
}
