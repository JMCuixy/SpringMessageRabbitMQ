package org.springframework.message.rabbitmq;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by XiuYin.Cui on 2018/4/28.
 */
@Component
public class HandlerListener {

    public <E> void handler(List<E> list){
        if (list != null && list.size() > 0){
            for (Object o : list){
                System.out.println("------------"+ o +"-----------");
            }
        }
    }
}
