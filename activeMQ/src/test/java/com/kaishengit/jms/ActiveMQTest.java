package com.kaishengit.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

public class ActiveMQTest {

    @Test
    public void messageProducer() throws JMSException {
        //1创建链接工厂
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2创建链接
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //3创建session会话(是否开启事务,设置签收机制)
        Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
        //4创建消息目的地
        Destination destination = session.createQueue("test Message");
        //5创建消息制造者
        MessageProducer messageProducer = session.createProducer(destination);
        //设置是否为持久模式
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //6创建消息
        TextMessage textMessage = session.createTextMessage("Hello,activeMQ");
        //7发送消息
        messageProducer.send(textMessage);
        //8释放资源
        messageProducer.close();
        session.close();
        connection.close();
    }

    @Test
    public void messageConsumer() throws JMSException, IOException {

        //1创建链接工厂
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2创建链接并开启
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //3创建session会话(是否开启事务,和签收机制)
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4创建目的地
        Destination destination = session.createQueue("test Message");
        //5创建消息消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
        //6获取消息
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        //7释放资源
        messageConsumer.close();
        session.close();
        connection.close();
    }

    /**
     * 1,rollback 测试重试机制
     */
    @Test
    public void messageConsumer1() throws JMSException, IOException {

        //1创建链接工厂
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2创建链接并开启
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //3创建session会话(是否开启事务,和签收机制)
        Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
        //4创建目的地
        Destination destination = session.createQueue("test Message");
        //5创建消息消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
        //6获取消息
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    if (textMessage.getText().equals("Hello,activeMQ")) {
                        throw new JMSException("故意抛出异常");
                    }
                    System.out.println(textMessage.getText());
                    session.commit();

                } catch (JMSException e) {
                    try {
                        session.rollback();
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
        });

        System.in.read();

        //7释放资源
        messageConsumer.close();
        session.close();
        connection.close();
    }

    /**
     * nocache 测试重试机制
     */
    @Test
    public void messageConsumer2() throws JMSException, IOException {

        //1创建链接工厂
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2创建链接并开启
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //3创建session会话(是否开启事务,和签收机制)
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4创建目的地
        Destination destination = session.createQueue("test Message");
        //5创建消息消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
        //6获取消息
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    if (textMessage.getText().equals("Hello,activeMQ")) {
                        throw new JMSException("故意抛出异常");
                    }
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    throw new RuntimeException("手动抛出异常");
                }
            }
        });
        System.in.read();
        //7释放资源
        messageConsumer.close();
        session.close();
        connection.close();
    }

    /**
     * recover 测试重试机制
     */
    @Test
    public void messageConsumer3() throws JMSException, IOException {

        //1创建链接工厂
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2创建链接并开启
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //3创建session会话(是否开启事务,和签收机制)
        Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
        //4创建目的地
        Destination destination = session.createQueue("test Message");
        //5创建消息消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
        //6获取消息
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    if("Hello,activeMQ".equals(text)) {
                        throw new JMSException("故意的异常");
                    }
                    System.out.println(">>> " + text);
                    //如果没有异常则签收
                    textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                    try {
                        //引起异常，触发重新投递机制
                        session.recover();
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        //7释放资源
        messageConsumer.close();
        session.close();
        connection.close();
    }

    /**以上为点对点模式
     * ------------------------------------------------------------
     */
    /**
     * 发布订阅模式
     * 消息发布者
     */
    @Test
    public void sendMessageToTopic() throws JMSException {

        String brokerUrl = "tcp://localhost:61616";
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test-topic");
        MessageProducer producer = session.createProducer(topic);
        TextMessage textMessage = session.createTextMessage("Hello,Topic");
        producer.send(textMessage);

        producer.close();
        session.close();
        connection.close();
    }

    /**
     * 消息订阅者
     * consumer 消费
     */
    @Test
    public void consumerMessageFromTopic() throws JMSException, IOException {


        String brokerUrl = "tcp://localhost:61616";
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test-topic");

        MessageConsumer messageConsumer = session.createConsumer(topic);

        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();

    }

}

