package com.sontan.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean//创建一个消息转换器，并返回给容器管理
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean//配置类方式创建交换器
    public Exchange fanout_exchange(){
        return ExchangeBuilder.fanoutExchange("fanout_exchange_config").build();
    }
    @Bean//配置类方式创建email队列
    public Queue fanout_queue_email(){
        return new Queue("fanout_queue_email_config");
    }
    @Bean//配置类方式创建sms队列
    public Queue fanout_queue_sms(){
        return new Queue("fanout_queue_sms_config");
    }

    @Bean//配置类方式创建绑定对象，将交换器和队列绑定在一起
    public Binding bindingEmail(){
        return
                BindingBuilder.bind(fanout_queue_email()).to(fanout_exchange()).with("").noargs();
    }
    @Bean
    public Binding bindingSms(){
        return
                BindingBuilder.bind(fanout_queue_sms()).to(fanout_exchange()).with("").noargs();
    }


}
