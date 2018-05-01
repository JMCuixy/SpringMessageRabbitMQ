package org.springframework.message.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XiuYin.Cui on 2018/4/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class HeadersExchangeTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void convertAndSend(){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("betty", "rubble");
        messageProperties.setHeader("fred", "flintstone");
        messageProperties.setHeader("barney", "rubble");

        String str = new String("Hello RabbitMQ");
        Message message = new Message(str.getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("headersExchange","",message);
    }

    @Test
    public void receiveAndConvert(){

        Message queue4 = rabbitTemplate.receive("queue4");
        System.out.println("第一个输出：" + new String(queue4.getBody()));
        Message queue5 = rabbitTemplate.receive("queue5");
        System.out.println("第三个输出：" + new String(queue5.getBody()));

    }

}
