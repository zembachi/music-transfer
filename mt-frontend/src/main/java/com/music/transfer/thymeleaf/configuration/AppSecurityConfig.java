package com.music.transfer.thymeleaf.configuration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppSecurityConfig {

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity http, KeycloakLogoutHandler keycloakLogoutHandler) {
        return http.authorizeHttpRequests(config ->
                config.requestMatchers("/api/**")
                        .authenticated()
                        .anyRequest()
                        .permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(config -> config.accessDeniedPage("/access-denied"))
                .oauth2Login(Customizer.withDefaults())
                .logout(config -> config
                        .addLogoutHandler(keycloakLogoutHandler)
                        .logoutUrl("/logout"))
                .build();
    }

}
