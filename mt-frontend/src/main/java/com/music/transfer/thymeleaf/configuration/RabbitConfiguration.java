package com.music.transfer.thymeleaf.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Bean
    public Queue rabbitQueue() {
        return new Queue("testQueue", false);
    }

    @Bean
    public Exchange rabbitExchange() {
        return new TopicExchange("testExchange", false, false);
    }

    @Bean
    public Binding rabbitBinding() {
        return BindingBuilder.bind(rabbitQueue()).to(rabbitExchange()).with("test.key").noargs();
    }

}
