package com.kaishengit;

import com.kaishengit.pojo.Customer;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ColumnTestCase {

    /*private Session session;
    @Before
    public void before() {
        session = HibernateUtil.getSession();
        session.beginTransaction();
    }
    @After
    public void after() {
        session.getTransaction().commit();
    }*/

    /*@Test
    public void save() {
        Customer customer = new Customer();
        customer.setName("测试");
        session.save(customer);
    }*/
    @Test
    public void update() throws InterruptedException {

        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Customer customer = (Customer) session.get(Customer.class,"297eadd66006f056016006f057330000", LockOptions.UPGRADE);
        customer.setName("测试3");

        Thread thread = new Thread(new Runnable() {
            public void run() {
                Session session2 = HibernateUtil.getSession();
                session2.getTransaction().begin();
                Customer customer1 = (Customer) session2.get(Customer.class,"297eadd66006f056016006f057330000");
                customer1.setName("测试2-5");
                session2.getTransaction().commit();
            }
        });
        thread.start();

        session.getTransaction().commit();
    }
}
