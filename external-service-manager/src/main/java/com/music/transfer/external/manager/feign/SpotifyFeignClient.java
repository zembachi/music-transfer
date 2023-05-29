package com.music.transfer.external.manager.feign;

import com.music.transfer.external.manager.config.properties.SpotifyFeignConfig;
import com.music.transfer.external.manager.dto.RequestSpotifyTokenDto;
import com.music.transfer.external.manager.dto.ResponseSpotifyTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "spotify", url = "${external.service.spotify.feign-url}", configuration = SpotifyFeignConfig.class)
public interface SpotifyFeignClient {

    @PostMapping(value = "api/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseSpotifyTokenDto getToken(@RequestBody RequestSpotifyTokenDto dto);

}
