package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Dept;
import com.kaishengit.entity.User;

import java.util.List;

public interface UserService {

    User findByNameAndPassword(String userName,String password);

    PageInfo<User> finAllUser(Integer pageNO);

    void addUser(User user);

    List<Dept> finAllDept();
}
