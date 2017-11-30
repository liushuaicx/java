package com.kaishengit.weixin.mq;

import com.alibaba.fastjson.JSON;
import com.kaishengit.weixin.WinXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class WeixinConsumer {


    @Autowired
    private WinXinUtil winXinUtil;
    @JmsListener(destination = "WeiXinMessageQueue")
    public void sendMessageToUser(String json) {

//        String json = "{\"id\":\"LiuShuai\",\"message\":\"Hello,Message from JMS\"}";
        Map<String,String> map = JSON.parseObject(json, HashMap.class);
        winXinUtil.sendMessage(map.get("message").toString(), Arrays.asList(1));

    }

}
