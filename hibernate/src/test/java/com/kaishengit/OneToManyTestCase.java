package com.kaishengit;

import com.kaishengit.pojo.Address;
import com.kaishengit.pojo.User;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class OneToManyTestCase {

//    private Session session;
////    @Before
//    public void before() {
//        session = HibernateUtil.getSession();
//        session.beginTransaction();
//    }
//    @After
//    public void after() {
//        session.getTransaction().commit();
//    }
//
////    @Test
//    public void findAddress() {
//
//        Address address = (Address) session.get(Address.class,2);
//        System.out.println(address.getAddress()+" --> "+address.getUser().getName());
//    }

    @Test
    public void findUser() {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        User user = (User) session.get(User.class,1);
        session.getTransaction().commit();

        Cache cache = HibernateUtil.getSessionFactory().getCache();
        cache.evictEntityRegion(User.class);

        Session session2 = HibernateUtil.getSession();
        session2.getTransaction().begin();
        User user2 = (User) session2.get(User.class,1);
        session2.getTransaction().commit();

//        System.out.println(user.getName());
//
//        Set<Address> addressSet = user.getAddressSet();
//        for (Address address : addressSet) {
//            System.out.println(address.getAddress());
//        }
    }
}
