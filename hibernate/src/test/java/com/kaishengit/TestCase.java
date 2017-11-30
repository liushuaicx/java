package com.kaishengit;

import com.kaishengit.pojo.Student;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class TestCase {

    private Session session;

    @Before
    public void before() {
        session = HibernateUtil.getSession();
        session.beginTransaction();
    }

    @Test
    public void load() {
        Student student = (Student) session.load(Student.class,8);
        //延长延时加载
        Hibernate.initialize(student);
        session.getTransaction().commit();
        System.out.println(student.getStuName());
    }
    @Test
    public void get() {

        //演示一级缓存
        Student student = (Student) session.get(Student.class,8);
        session.clear();
        Student student2 = (Student) session.get(Student.class,8);
        session.evict(student2);
        Student student3 = (Student) session.get(Student.class,8);

        System.out.println(session.contains(student));
        session.getTransaction().commit();
    }

    @Test
    public void save() {

        Student student = new Student();
        student.setStuName("tom");
        student.setStuAddress("UK");
        session.persist(student);

        System.out.println(student.getStuId());
        session.getTransaction().commit();
    }





}
