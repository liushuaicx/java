package com.kaishengit;

import com.kaishengit.entity.Student;
import com.kaishengit.mapper.StudentMapper;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class MyBatisStudentMapperTestCase {

    @Test
    public void findAll() {

        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        //根据接口的classpath动态创建接口的实现类
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> studentList = studentMapper.findAll();

        for (Student student : studentList) {
            System.out.println(student);
        }
        sqlSession.close();
//        ===================================
        SqlSession sqlSession2 = MyBatisUtil.getSqlSession();
        //根据接口的classpath动态创建接口的实现类
        StudentMapper studentMapper2 = sqlSession2.getMapper(StudentMapper.class);
        List<Student> studentList2 = studentMapper2.findAll();

        for (Student student : studentList2) {
            System.out.println(student);
        }
        sqlSession.close();



    }
    private SqlSession sqlSession;
    private StudentMapper studentMapper;

    @Before
    public void init() {
        sqlSession = MyBatisUtil.getSqlSession();
        //根据sqlSession动态创建接口的实现类
        studentMapper = sqlSession.getMapper(StudentMapper.class);
    }

    @After
    public void close() {

        sqlSession.close();
    }

    @Test
    public void save() {

        Student student = new Student("王思望","北京");
        int a = studentMapper.save(student);
        System.out.println(a);
        System.out.println(student.getStuId());
        sqlSession.commit();
    }

    @Test
    public void findById() {

        Student student = studentMapper.findById(6);
        System.out.println(student);
    }

    @Test
    public void delete() {

        studentMapper.delete(2);
        sqlSession.commit();
    }

    @Test
    public void update() {

        Student student = studentMapper.findById(6);
        student.setStuName("陆露");
        student.setStuAddress("上海");

        studentMapper.update(student);
        sqlSession.commit();
    }
    @Test
    public void page() {

        List<Student> studentList = studentMapper.page(0,3);
        for (Student student : studentList) {
            System.out.println(student);
        }

    }

}
