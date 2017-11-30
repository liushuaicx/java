package com.kaishengit;

import com.kaishengit.pojo.Student;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

import java.util.List;

public class StudentTestCase {

    @Test
    public void save() {
        //读取配置文件 hibernate.cfg.xml
        Configuration configuration = new Configuration().configure();

        //创建SessionFactory
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        //创建session
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Student student = new Student();
        student.setStuName("tom");
        student.setStuAddress("UK");
        session.save(student);

        session.getTransaction().commit();
    }

    @Test
    public void findById() {

        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Student student = (Student) session.get(Student.class,8);
        System.out.println(student);

        session.getTransaction().commit();

    }

    @Test
    public void findAll() {

        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        //hql
        String hql = "from Student order by stuId desc ";
        Query query = session.createQuery(hql);
        List<Student> studentList = query.list();

        for (Student student : studentList) {

            System.out.println(student);
        }
        session.getTransaction().commit();
    }

    @Test
    public void delete() {

        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        //删除是先获得对象在删除对象
        Student student = (Student) session.get(Student.class,9);
        session.delete(student);

        session.getTransaction().commit();
    }

    @Test
    public void update() {

        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Student student = (Student) session.get(Student.class,8);
        student.setStuAddress("US");

        session.getTransaction().commit();

    }



}
