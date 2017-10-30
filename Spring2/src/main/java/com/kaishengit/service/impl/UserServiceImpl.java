package com.kaishengit.service.impl;

import com.kaishengit.dao.UserDao;
import com.kaishengit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void save() {

    }
}
