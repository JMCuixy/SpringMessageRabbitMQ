package org.springframework.message.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.plugin.javascript.navig.LinkArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiuYin.Cui on 2018/4/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class FanoutExchangeTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void convertAndSend(){
        List<String> list = new ArrayList<>();
        list.add("java");
        list.add("python");
        list.add("c++");
        rabbitTemplate.convertAndSend("fanoutExchange", "任意的Routing key", list);
    }

    @Test
    public void receiveAndConvert(){
        printList((List)rabbitTemplate.receiveAndConvert("queue5"));
        printList((List)rabbitTemplate.receiveAndConvert("queue6"));
    }


    private <E> void printList(List<E> list){
        if (list != null && list.size() > 0){
            for (Object o : list){
                System.out.println("-----------------"+ o +"---------------");
            }
        }
    }
}
