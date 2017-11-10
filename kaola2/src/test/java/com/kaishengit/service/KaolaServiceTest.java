package com.kaishengit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class KaolaServiceTest {

    @Autowired
    private KaolaService kaolaService;
    @Test
    public void findList() {

        System.out.println(kaolaService.findList(1));

    }

}