package com.kaishengit.springboot1.service.impl;

import com.kaishengit.springboot1.mapper.UserMapper;
import com.kaishengit.springboot1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liushuai
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


}
