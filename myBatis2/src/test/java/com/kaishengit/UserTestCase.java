package com.kaishengit;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.User;
import com.kaishengit.entity.UserExample;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserTestCase {

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
    public void insert() {

        User user = new User();
        user.setUserName("rose");
        user.setSortId(1);
        userMapper.insertSelective(user);
        sqlSession.commit();

    }

    @Test
    public void delete() {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(3);

        userMapper.deleteByExample(userExample);
        sqlSession.commit();
    }

    @Test
    public void deleteById() {

        userMapper.deleteByPrimaryKey(2);
        sqlSession.commit();
    }

    @Test
    public void findById() {

        User user = userMapper.selectByPrimaryKey(1);

        System.out.println(user);

    }

    @Test
    public void findAll() {

        UserExample userExample = new UserExample();
        List<User> userList = userMapper.selectByExample(userExample);

        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void update() {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(4);

        User user = new User("ailis", "123", 1);
        userMapper.updateByExampleSelective(user, userExample);
        sqlSession.commit();

    }

    @Test
    public void updateByParams() {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andSortIdEqualTo(2);

        User user = new User();
        user.setUserPassword("123");

        userMapper.updateByExampleSelective(user, userExample);
        sqlSession.commit();

    }

    @Test
    public void findByOr() {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andSortIdEqualTo(2);
        userExample.setOrderByClause("id desc");

        List<User> userList = userMapper.selectByExample(userExample);

        for (User user : userList) {
            System.out.println(user);
        }

    }

    @Test
    public void page() {

        PageHelper.startPage(1, 3);

        UserExample userExample = new UserExample();
        List<User> userList = userMapper.selectByExample(userExample);

        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void pageInfo() {

        PageHelper.startPage(2,3);
        UserExample userExample = new UserExample();
        List<User> userList = userMapper.selectByExample(userExample);
        //转化为pageInfo对象
        /*向前端传值时使用*/
        PageInfo<User> pageInfo = new PageInfo<User>(userList);
        for (User user : pageInfo.getList()) {
            System.out.println(user);
        }

    }
}
