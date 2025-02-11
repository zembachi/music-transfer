package com.music.transfer.telegram.config.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "telegram.bot")
public class TelegramBotProperties {

    @NotNull
    private String name;

    @NotNull
    private String token;

}
