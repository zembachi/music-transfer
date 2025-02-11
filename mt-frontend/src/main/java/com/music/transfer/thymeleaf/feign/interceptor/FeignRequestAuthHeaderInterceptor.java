package com.music.transfer.thymeleaf.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeignRequestAuthHeaderInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String TOKEN_TYPE = "Bearer ";

    private static final String KEYCLOAK_REG_ID = "keycloak";

    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
            final var oauthToken = (OAuth2AuthenticationToken) authentication;
            final var registrationId = oauthToken.getAuthorizedClientRegistrationId();
            if (KEYCLOAK_REG_ID.equals(registrationId)) {
                final var client =
                        oAuth2AuthorizedClientService.loadAuthorizedClient(registrationId, oauthToken.getName());
                final var accessToken = client.getAccessToken().getTokenValue();
                requestTemplate.header(AUTHORIZATION_HEADER, TOKEN_TYPE + accessToken);
            }
        }
    }

}
