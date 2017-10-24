package com.kaishengit;

import com.kaishengit.entity.User;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MyBatisUserTestCase {

    private SqlSession sqlSession;
    private UserMapper userMapper;

    @Before
    public void init() {
        sqlSession = MyBatisUtil.getSqlSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }
    @After
    public void close() {
        sqlSession.close();
    }

    @Test
    public void findAll() {


        List<User> userList = userMapper.findAll();

        for (User user : userList) {

            System.out.println(user);
        }
    }

}
