package com.kaishengit.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisUtil {

    private  static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            //读取calsspath中mybatis.xml配置文件
            Reader reader = Resources.getResourceAsReader("mybatis.xml");
            //创建sqlSessionFactory ,根据reader
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSessionFactory getSqlSessionFactory() {

        return sqlSessionFactory;
    }
    public static SqlSession getSqlSession() {
        return getSqlSessionFactory().openSession();
    }

}
