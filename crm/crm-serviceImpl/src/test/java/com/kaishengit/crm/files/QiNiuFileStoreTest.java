package com.kaishengit.crm.files;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-service.xml")
public class QiNiuFileStoreTest {

    @Test
    public void download() throws IOException {

        QiNiuFileStore qiNiuFileStore = new QiNiuFileStore();
        byte[] bytes = qiNiuFileStore.getFile("FpVJQAqeokREdDdxbiKuO5HFTn5F");

    }

}
