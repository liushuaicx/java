package com.kaishengit;

import com.kaishengit.pojo.Card;
import com.kaishengit.pojo.Person;
import com.kaishengit.pojo.Stu;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OneToOneTestCase {

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
    public void save() {

        Person person = new Person();
        person.setName("jack");
        session.save(person);

        Card card = new Card();
        card.setCardNum("1265489");
        card.setPerson(person);

        session.save(card);
    }

    @Test
    public void find() {

        Person person = (Person) session.get(Person.class,1);
        System.out.println(person.getName()+" --> "+person.getCard().getCardNum());

    }
    @Test
    public void delete() {

        Person person = (Person) session.get(Person.class,1);
        session.delete(person);
    }

    @Test
    public void select() {

        Stu stu = (Stu) session.get(Stu.class,5);
        stu.getTeacherSet();

    }

}
