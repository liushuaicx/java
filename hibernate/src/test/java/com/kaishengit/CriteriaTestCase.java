package com.kaishengit;

import com.kaishengit.pojo.Student;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Criteria:标准
 */
public class CriteriaTestCase {

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
    public void findAll() {
        Criteria criteria = session.createCriteria(Student.class);
        List<Student> studentList = criteria.list();

        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    @Test
    public void findById() {
        Criteria criteria = session.createCriteria(Student.class);

        criteria.add(Restrictions.eq("stuName","tom"));
        criteria.add(Restrictions.eq("stuId",11));
        List<Student> studentList = criteria.list();

        for (Student student : studentList) {
            System.out.println(student);
        }
    }
    @Test
    public void find() {
        Criteria criteria = session.createCriteria(Student.class);

        criteria.add(Restrictions.or(Restrictions.eq("stuName","tom"),
                                    Restrictions.eq("stuName","陆露")));
        List<Student> studentList = criteria.list();

        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    /**
     * 返回结果集为单个
     */
    @Test
    public void returnOne() {

        Criteria criteria = session.createCriteria(Student.class);
        criteria.add(Restrictions.eq("stuId",11));

        Student student = (Student) criteria.uniqueResult();
        System.out.println(student);
    }

    /**
     * 分页
     */
    @Test
    public void page() {

        Criteria criteria = session.createCriteria(Student.class);
        criteria.setFirstResult(0);
        criteria.setMaxResults(4);

        List<Student> studentList = criteria.list();
        for (Student student : studentList) {
            System.out.println(student);
        }

    }

    /**
     * 聚合函数求和
     */
    @Test
    public void sum() {

        Criteria criteria = session.createCriteria(Student.class);
        criteria.setProjection(Projections.sum("stuId"));
        Object o = criteria.uniqueResult();
        System.out.println(o);
    }

    @Test
    public void projections() {

        Criteria criteria = session.createCriteria(Student.class);
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.max("stuId"));
        projectionList.add(Projections.min("stuId"));

        criteria.setProjection(projectionList);

        Object[] objects = (Object[]) criteria.uniqueResult();

        System.out.println("max -->"+objects[0]);
        System.out.println("min -->"+objects[1]);
    }

    /**
     * 排序
     */
    @Test
    public void rank() {

        Criteria criteria = session.createCriteria(Student.class);
        criteria.addOrder(Order.desc("stuId"));
        List<Student> studentList = criteria.list();

        for (Student student : studentList) {
            System.out.println(student);
        }

    }
    /**
     * -------------------------------------------------------------------------
     * 使用原生sql查询
     */

    @Test
    public void select() {

        String sql = "select * from t_student where stu_id = ?";
        SQLQuery sqlQuery = session.createSQLQuery(sql).addEntity(Student.class);
        sqlQuery.setParameter(0,6);

        Student student = (Student) sqlQuery.uniqueResult();
        System.out.println(student);
    }
    @Test
    public void selectOne() {

        String sql = "select * from t_student where stu_id = :id";
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setParameter("id",6);

        Object[] objects = (Object[]) sqlQuery.uniqueResult();
        System.out.println(objects[2]);
    }

}
