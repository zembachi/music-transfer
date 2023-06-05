package com.music.transfer.external.manager.feign;

import com.music.transfer.external.manager.config.properties.SpotifyFeignConfig;
import com.music.transfer.external.manager.dto.ResponseSpotifyGetPlaylistDto;
import com.music.transfer.external.manager.dto.ResponseSpotifyGetPlaylistsDto;
import com.music.transfer.external.manager.dto.ResponseSpotifyGetProfileDto;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "spotify-api", url = "${external.service.spotify.feign-api-url}", configuration = SpotifyFeignConfig.class)
public interface SpotifyApiFeignClient {

    @GetMapping(value = "/me")
    @Headers("Authorization: Bearer {token}")
    ResponseSpotifyGetProfileDto getProfile(@Param("token") String token);

    @GetMapping(value = "/users/{userId}/playlists")
    @Headers("Authorization: Bearer {token}")
    ResponseSpotifyGetPlaylistsDto getUserPlaylists(@Param("token") String token,
                                                    @PathVariable("userId") String userId,
                                                    Integer limit,
                                                    Integer offset);

    @GetMapping(value="/playlists/{playlistId}/tracks")
    @Headers("Authorization: Bearer {token}")
    ResponseSpotifyGetPlaylistDto getUserPlaylistInfo(@Param("token") String token,
                                                      @PathVariable("playlistId") String playlistId,
                                                      Integer limit,
                                                      Integer offset,
                                                      String fields);

}
