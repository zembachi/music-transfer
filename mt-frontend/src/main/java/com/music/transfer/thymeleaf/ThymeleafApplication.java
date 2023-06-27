package com.music.transfer.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ThymeleafApplication {

    public static void main (String[] args) {
        SpringApplication.run(ThymeleafApplication.class, args);
    }
}
