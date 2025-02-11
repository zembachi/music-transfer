package com.music.transfer.external.manager.feign;

import com.music.transfer.external.manager.config.SpotifyFeignConfig;
import com.music.transfer.external.manager.dto.ResponseSpotifyGetPlaylistDto;
import com.music.transfer.external.manager.dto.ResponseSpotifyGetPlaylistsDto;
import com.music.transfer.external.manager.dto.ResponseSpotifyGetProfileDto;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "spotify-api", url = "${external.service.spotify.feign-api-url}", configuration = SpotifyFeignConfig.class)
public interface SpotifyApiFeignClient {

    @GetMapping(value = "/me")
    ResponseSpotifyGetProfileDto getProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(value = "/users/{userId}/playlists")
    ResponseSpotifyGetPlaylistsDto getUserPlaylists(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                    @PathVariable("userId") String userId,
                                                    @RequestParam("limit") Integer limit,
                                                    @RequestParam("offset") Integer offset);

    @GetMapping(value="/playlists/{playlistId}/tracks")
    @Headers("Authorization: Bearer {token}")
    ResponseSpotifyGetPlaylistDto getUserPlaylistInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                      @PathVariable("playlistId") String playlistId,
                                                      @RequestParam("limit") Integer limit,
                                                      @RequestParam("offset") Integer offset,
                                                      @RequestParam("fields") String fields);

}
