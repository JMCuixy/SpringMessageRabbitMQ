package org.springframework.message.rabbitmq;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by XiuYin.Cui on 2018/4/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RabbitMQTest {


    @Test
    public void test() throws InterruptedException {
        ConnectionFactory cf = new CachingConnectionFactory("127.0.0.1", 5672);
        ((CachingConnectionFactory) cf).setUsername("guest");
        ((CachingConnectionFactory) cf).setPassword("guest");

        // set up the queue, exchange, binding on the broker
        //admin
        RabbitAdmin admin = new RabbitAdmin(cf);
        //queue
        Queue queue = new Queue("myQueue");
        admin.declareQueue(queue);
        //exchange
        TopicExchange topicExchange = new TopicExchange("myExchange");
        admin.declareExchange(topicExchange);
        //bindings
        Binding binding = BindingBuilder.bind(queue).to(topicExchange).with("foo.*");
        admin.declareBinding(binding);

        // set up the listener and container
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);
        Object listener = new Object(){
            public void handleMessage(String foo){
                System.out.println("---------------------------------" + foo);
            }
        };
        MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
        container.setMessageListener(adapter);
        container.setQueueNames("myQueue");
        container.start();

        //send something
        RabbitTemplate template = new RabbitTemplate(cf);
        template.convertAndSend("myExchange", "foo.bar" ,"Hello RabbitMQ");
        Thread.sleep(1000);
        container.stop();




    }

}
