package com.kaishengit.weixin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-weixin.xml")
public class WinXinUtilTest {


    @Autowired
    private WinXinUtil winXinUtil;

    @Test
    public void getAccessToken() {
        String ContactsAccessToken = winXinUtil.getAccessToken(WinXinUtil.TOKEN_TYPE_CONTACTS);
        String NormalAccessToken = winXinUtil.getAccessToken(WinXinUtil.TOKEN_TYPE_NORMAL);

        System.out.println(ContactsAccessToken);
        System.out.println(NormalAccessToken);

    }

    @Test
    public void createDept() {

        winXinUtil.createDept("测试部门添加",1,103);

    }

    @Test
    public void deleteDept() {
        winXinUtil.deleteDept(9);
    }

    @Test
    public void addUser() {
        winXinUtil.createUser(12,"王辉", Arrays.asList(102),"15150178396");
    }

    @Test
    public void deleteUser() {
        winXinUtil.deleteUser(12);
    }

    @Test
    public void sendMessage() {
        winXinUtil.sendMessage("王辉",Arrays.asList(12));
    }

}
