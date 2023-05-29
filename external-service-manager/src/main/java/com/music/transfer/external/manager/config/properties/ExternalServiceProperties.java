package com.music.transfer.external.manager.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ConfigurationProperties("external.service")
@Data
@Validated
public class ExternalServiceProperties {

    @NotNull
    private Spotify spotify;

    @NotNull
    /*
      properties for vk music connection
     */
    private VkMusic vkMusic;

    @Data
    public static class Spotify {

        @NotBlank
        private String url;

        @NotBlank
        private String redirectUrl;

        @NotBlank
        private String scope;

        @NotBlank
        private String responseType;

        @NotBlank
        private String clientId;

    }

    @Data
    public static class VkMusic {

        @NotBlank
        /*
          url for vk music
         */
        private String url;

    }

}
