package com.music.transfer.external.manager.config.properties;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;

public class SpotifyFeignConfig {

    @Bean
    public Decoder spotifyDecoder(ObjectMapper objectMapper) {
        return new JacksonDecoder(getSnakeCaseMapper(objectMapper));
    }

    @Bean
    public Encoder spotifyEncoder(ObjectMapper objectMapper) {
        return new FormEncoder(new JacksonEncoder(getSnakeCaseMapper(objectMapper)));
    }

    public ObjectMapper getSnakeCaseMapper(ObjectMapper objectMapper) {
        var snakeCaseObjectMapper  = objectMapper.copy();
        snakeCaseObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        snakeCaseObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return snakeCaseObjectMapper;
    }
}
