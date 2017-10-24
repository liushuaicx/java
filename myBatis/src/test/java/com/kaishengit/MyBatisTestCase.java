package com.kaishengit;

import com.kaishengit.entity.Student;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class MyBatisTestCase {

    @Test
    public void save() throws IOException {

//        读取classpath中的mybatis配置文件
        Reader reader = Resources.getResourceAsReader("mybatis.xml");
//        根据reader创建sqlSessionFactory 对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//        创建sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        Student student = new Student("jack","焦作");

        sqlSession.insert("com.kaishengit.mapper.StudentMapper.save",student);

        sqlSession.commit();

        sqlSession.close();
    }
    @Test
    public void findAll() {

        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        List<Student> stuList = sqlSession.selectList("com.kaishengit.mapper.StudentMapper.findAll");

        for(Student student : stuList) {
            System.out.println(student);
        }
        sqlSession.close();
    }
    @Test
    public void delete() {

        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        sqlSession.delete("com.kaishengit.mapper.StudentMapper.delete",1);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void update() {

        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        Student student =sqlSession.selectOne("com.kaishengit.mapper.StudentMapper.findById",2);

        student.setStuName("rose");
        student.setStuAddress("郑州");

        sqlSession.update("com.kaishengit.mapper.StudentMapper.update",student);

        sqlSession.commit();

        sqlSession.close();
    }
    @Test
    public void findById() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        Student student =sqlSession.selectOne("com.kaishengit.mapper.StudentMapper.findById",2);
        System.out.println(student);
        sqlSession.close();
    }



}
