package com.example.demo.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "item_queue")
public class MsgConsumer {

    @RabbitHandler
    public void received(String msg){
        System.out.println("消费者消费消息了："+msg);
        //TODO 这里可以做异步的工作
    }

}
