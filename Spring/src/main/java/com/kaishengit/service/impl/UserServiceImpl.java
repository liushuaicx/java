package com.kaishengit.service.impl;

import com.kaishengit.dao.UserDao;
import com.kaishengit.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author 刘帅
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl () {};

    public UserServiceImpl (UserDao userDao) {

        this.userDao = userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {
        System.out.println("save........");
    }
    /* private int id;
    private String name;
    private List<String> nameList;
    private Set<UserDao> userDaoSet;
    private Map<String, UserDao> userDaoMap;
    private Properties properties;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public void setUserDaoSet(Set<UserDao> userDaoSet) {
        this.userDaoSet = userDaoSet;
    }

    public void setUserDaoMap(Map<String, UserDao> userDaoMap) {
        this.userDaoMap = userDaoMap;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setUserDao1(UserDao userDao) {
        this.userDao = userDao;
    }*/


    /*@Override
    public void save() {

        userDao.Hello();

    }*/

}
