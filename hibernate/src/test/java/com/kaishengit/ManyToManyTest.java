package com.kaishengit;

import com.kaishengit.pojo.Stu;
import com.kaishengit.pojo.Teacher;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class ManyToManyTest {

    private Session session;
    @Before
    public void before() {
        session = HibernateUtil.getSession();
        session.beginTransaction();
    }
    @After
    public void after() {
        session.getTransaction().commit();
    }

    @Test
    public void find() {

        Stu stu = (Stu) session.get(Stu.class,3);
        System.out.println(stu.getName());
        Set<Teacher> teacherSet = stu.getTeacherSet();
        for (Teacher teacher : teacherSet) {
            System.out.println(teacher.getName());
        }
    }

    @Test
    public void save() {

        Teacher teacher = new Teacher();
        teacher.setName("teacher2");
        session.save(teacher);

        Stu stu = (Stu) session.get(Stu.class,3);
        Set<Teacher> teacherSet = stu.getTeacherSet();
        teacherSet.add(teacher);

        stu.setTeacherSet(teacherSet);
    }

    /**
     * 最佳事件,先存不维护关系的对象,再存维护关系的对象
     */
    @Test
    public void save2() {

        Teacher teacher = new Teacher();
        teacher.setName("ttt1");

        Teacher teacher2 = new Teacher();
        teacher2.setName("ttt2");

        Stu stu = new Stu();
        stu.setName("sss1");

        Stu stu2 = new Stu();
        stu2.setName("sss2");

        Set<Teacher> teacherSet = new HashSet<Teacher>();
        teacherSet.add(teacher);
        teacherSet.add(teacher2);

        stu.setTeacherSet(teacherSet);
        stu2.setTeacherSet(teacherSet);

        session.save(teacher);
        session.save(teacher2);
        session.save(stu);
        session.save(stu2);
    }
}
