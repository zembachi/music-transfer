package com.music.transfer.telegram.config;

import com.music.transfer.telegram.config.properties.TelegramBotProperties;
import com.music.transfer.telegram.telegram.TelegramBot;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.TelegramOkHttpClientFactory;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
@RequiredArgsConstructor
public class TelegramConfiguration {

    @Bean
    public TelegramClient telegramClient(TelegramBotProperties telegramBotProperties) {
        return new OkHttpTelegramClient(telegramBotProperties.getToken());
    }

}
