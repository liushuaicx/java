package com.kaishengit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Dept;
import com.kaishengit.entity.DeptExample;
import com.kaishengit.entity.User;
import com.kaishengit.entity.UserExample;
import com.kaishengit.mapper.DeptMapper;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public User findByNameAndPassword(String userName, String password) {

        return userMapper.findByNameAndPassword(userName,password);
    }

    @Override
    public PageInfo<User> finAllUser(Integer pageNO) {

        PageHelper.startPage(pageNO,10);
        List<User> list = userMapper.selectAllWithDept();

        return new PageInfo<>(list);

    }

    @Override
    public void addUser(User user) {

        userMapper.insertSelective(user);
    }

    @Override
    public List<Dept> finAllDept() {

        return deptMapper.selectByExample(new DeptExample());
    }
}
