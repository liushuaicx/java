package com.kaishengit;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SpringConsumer {

    @JmsListener(destination = "spring-QueueTest")
    public void doSomething(String message) {
        System.out.println(message);
    }

    @JmsListener(destination = "spring-topic",containerFactory = "topicJmsListenerContainerFactory")
    public void consumerFromTopic(String message) {

        System.out.println(message);
    }


}
