package com.example.demo.rabbitmq;

import com.example.demo.service.DemoService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "${biz.user.topic}")
@ConditionalOnExpression("${component.mq}")
public class MsgConsumer {

    @Autowired
    DemoService demoService;

    @RabbitHandler
    public void received(String msg){
        System.out.println("消费者消费消息了："+msg);
        demoService.doSomeThing();
        //TODO 这里可以做异步的工作
    }

}
