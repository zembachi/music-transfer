package com.music.transfer.external.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableFeignClients
@EnableJpaRepositories
@EnableTransactionManagement
public class SpringExternalManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringExternalManagerApplication.class, args);
    }

}
