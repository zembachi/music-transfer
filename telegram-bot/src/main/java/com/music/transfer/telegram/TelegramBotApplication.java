package com.music.transfer.telegram;

import com.music.transfer.telegram.config.properties.TelegramBotProperties;
import com.music.transfer.telegram.telegram.TelegramBot;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties
@EnableFeignClients
public class TelegramBotApplication {

    public static void main(String[] args) throws TelegramApiException {
        ConfigurableApplicationContext context = SpringApplication.run(TelegramBotApplication.class, args);
        var app = context.getBean(TelegramBotsLongPollingApplication.class);
        var bot = context.getBean(TelegramBot.class);
        var settings = context.getBean(TelegramBotProperties.class);
        app.registerBot(settings.getToken(), bot);
    }

}
