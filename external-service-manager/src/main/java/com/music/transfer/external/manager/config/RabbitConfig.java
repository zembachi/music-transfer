package com.music.transfer.external.manager.config;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitConfig {

    @Bean
    public Queue rabbitQueue() {
        return new Queue("testQueue", false);
    }

    @RabbitListener(queues = "testQueue")
    public void listen(@NotNull String message) {
        log.info("message: " + message);
    }
}
