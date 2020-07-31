package com.example.demo.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MsgConsumer {

    @RabbitListener(queues = "item_queue")
    public void msg(String msg){
        System.out.println("消费者消费消息了："+msg);
        //TODO 这里可以做异步的工作
    }

}
