package com.kaishengit;

import com.kaishengit.entity.KaolaType;
import com.kaishengit.service.KaolaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class KaolaServiceTest {

    @Autowired
    private KaolaService kaolaService;

    @Test
    public void finAllType() {
        List<KaolaType> kaolaTypeList = kaolaService.findAllType();

        for (KaolaType kaolaType : kaolaTypeList) {

            System.out.println(kaolaType);
        }
    }
}
