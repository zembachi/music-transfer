package com.music.transfer.telegram.config.properties;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "oidc")
@Data
@Validated
public class OidcProperties {

    @NotNull
    private OidcKeycloakProperties keycloak;

    @NotNull
    private OidcTokenProperties token;

    @Data
    public static class OidcKeycloakProperties {

        @NotNull
        private String clientId;

        @NotNull
        private String scope;

        @NotNull
        private String callback;

        @NotNull
        private String baseUrl;

        @NotNull
        private String realm;

        @NotNull
        private String clientSecret;

    }

    @Data
    public static class OidcTokenProperties {

        @NotNull
        @Positive
        private Integer lifetime;

        @NotNull
        @Positive
        private Long offset;

    }

}
