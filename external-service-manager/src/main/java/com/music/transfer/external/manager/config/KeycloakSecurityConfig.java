package com.music.transfer.external.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableMethodSecurity
public class KeycloakSecurityConfig {

    @Bean
    Converter<Jwt, List<SimpleGrantedAuthority>> authoritiesConverter(GenericConversionService genericConversionService) {
        Converter<Jwt, List<SimpleGrantedAuthority>> jwtListConverter = jwt -> {
            final var realmAccess = (Map<String, Object>) jwt.getClaims().getOrDefault("realm_access", Map.of());
            final var clientRoles = (List<String>) realmAccess.getOrDefault("roles", List.of());

            return clientRoles.stream().map(SimpleGrantedAuthority::new).toList();
        };
        genericConversionService.addConverter(Jwt.class, List.class, jwtListConverter);
        return jwtListConverter;
    }

    @Bean
    Converter<Jwt, JwtAuthenticationToken> authenticationConverter(Converter<Jwt, ? extends Collection<? extends GrantedAuthority>> authoritiesConverter) {
        return jwt -> new JwtAuthenticationToken(jwt, authoritiesConverter.convert(jwt), jwt.getClaimAsString(StandardClaimNames.PREFERRED_USERNAME));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           Converter<Jwt, ? extends AbstractAuthenticationToken> authenticationConverter) throws Exception {
        return http.authorizeHttpRequests(config ->
                config.requestMatchers(HttpMethod.GET, "/api/**")
                        .hasAuthority("api_read")
                        .requestMatchers(HttpMethod.POST, "/api/**")
                        .hasAuthority("api_write")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(server -> server.jwt(jwt -> jwt.jwtAuthenticationConverter(authenticationConverter)))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}
