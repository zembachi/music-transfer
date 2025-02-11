package com.music.transfer.telegram.config;

import com.github.scribejava.apis.KeycloakApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.music.transfer.telegram.config.properties.OidcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OidcProperties.class)
public class OidcConfiguration {

    @Bean
    OAuth20Service oAuthService(OidcProperties properties) {
        final var keycloakProperties = properties.getKeycloak();
        return new ServiceBuilder(keycloakProperties.getClientId())
                .apiSecret(keycloakProperties.getClientSecret())
                .defaultScope(keycloakProperties.getScope())
                .callback(keycloakProperties.getCallback())
                .build(KeycloakApi.instance(keycloakProperties.getBaseUrl(), keycloakProperties.getRealm()));
    }

}
