package com.sontan;

import com.sontan.controller.HelloController;
import com.sontan.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloTest {

    @Autowired //自动注入
    private HelloController helloController;

    @Test
    public void HelloControllerTest(){
        System.out.println(helloController.helloSpringboot());
    }

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void amqpAdnin(){
        //定义fanout类型的交换器
        amqpAdmin.declareExchange(new FanoutExchange("fanout_exchange"));
        //定义两个默认持久化队列，处理email和sms
        amqpAdmin.declareQueue(new Queue("fanout_queue_email"));
        amqpAdmin.declareQueue(new Queue("fanout_queue_sms"));
        //将队列分别与交换器进行绑定
        amqpAdmin.declareBinding(new Binding("fanout_queue_email", Binding.DestinationType.QUEUE,"fanout_exchange","",null));
        amqpAdmin.declareBinding(new Binding("fanout_queue_sms", Binding.DestinationType.QUEUE,"fanout_exchange","",null));
    }

    //发布消息
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void psubPublisher(){
        User user=new User();
        user.setId(6778999);
        user.setUsername("老六778999");
        rabbitTemplate.convertAndSend("fanout_exchange","",user);
        rabbitTemplate.convertAndSend("fanout_exchange_config","",user);
        rabbitTemplate.convertAndSend("fanout_exchange_zhujie","",user);
        //发送消息，并带对应的routing key
        rabbitTemplate.convertAndSend("routing_exchange","error_routing_key",user);
    }
}
