package org.springframework.message.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiuYin.Cui on 2018/4/27.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TopicExchange {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public void convertAndSend(){
        List<String> list = new ArrayList<>();
        list.add("java");
        list.add("python");
        list.add("c++");
        rabbitTemplate.convertAndSend("topicExchange","routing.123", list);
    }


    @Test
    public void receiveAndConvert(){
        List<String> queue2List =(List) rabbitTemplate.receiveAndConvert("queue2");
        printList(queue2List);

        System.out.println("----------------华丽的分隔符-----------------");

        List<String> queue3List =(List) rabbitTemplate.receiveAndConvert("queue3");
        printList(queue3List);

    }


    private <E> void printList(List<E> list){
        if (list != null && list.size() > 0){
            for (Object o : list){
                System.out.println("-----------------"+ o +"---------------");
            }
        }
    }
}
