package com.sontan.Service;

import com.sontan.domain.User;
import jdk.nashorn.internal.runtime.regexp.joni.Warnings;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {
    //消费者：接收、处理邮件服务
    @RabbitListener(queues = {"fanout_queue_email","fanout_queue_sms"})
    public void psubConsumerEmail(Message message){
        byte[] body=message.getBody();
        String s=new String(body);
        System.out.println("API方式邮件短信业务接收到消息："+s);
    }


    @RabbitListener(queues = {"fanout_queue_email_config","fanout_queue_sms_config"})
    public void psubConsumerEmailpeizhi(Message message){
        byte[] body=message.getBody();
        String s=new String(body);
        System.out.println("配置方式邮件短信业务接收到消息："+s);
    }


    //注解方式：将交换机和对象的创建和绑定，以及消费函数的定义一起完成
    //bindings={}多个队列与交换机绑定
    @RabbitListener(bindings ={
            @QueueBinding(
                    value = @Queue("fanout_queue_email_zhujie"),
                    exchange = @Exchange(value = "fanout_exchange_zhujie",type = "fanout")),
            @QueueBinding(
                    value = @Queue("fanout_queue_sms_zhujie"),
                    exchange = @Exchange(value = "fanout_exchange_zhujie",type = "fanout"))
    })
    public void psubConsumerEmailAno(User user) {
        System.out.println("注解方式邮件短信业务接收到消息： "+user);
    }

    //路由模式
    @RabbitListener(bindings ={
            @QueueBinding(value =@Queue("routing_queue_error"),
            exchange =@Exchange(value = "routing_exchange",type = "direct"),
            key = "error_routing_key"),
            @QueueBinding(value =@Queue("routing_queue_error"),
            exchange =@Exchange(value = "routing_exchange",type = "direct"),
            key = {"info_routing_key","Warning_routing_key"})
    })
    public void routingConsumerError(Message message) {
        System.out.println("接收到error级别日志消息： "+message);
    }

}
