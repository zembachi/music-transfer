package com.music.transfer.external.manager.handler.impl;

import com.music.transfer.dto.ExternalServiceTokenDto;
import com.music.transfer.dto.ExternalServiceType;
import com.music.transfer.dto.FoundTrackDto;
import com.music.transfer.dto.PlaylistDto;
import com.music.transfer.external.manager.dto.RequestRefreshSpotifyTokenDto;
import com.music.transfer.external.manager.dto.RequestSpotifyTokenDto;
import com.music.transfer.dto.TrackDto;
import com.music.transfer.external.manager.config.properties.ExternalServiceProperties;
import com.music.transfer.external.manager.entity.ExternalServiceToken;
import com.music.transfer.external.manager.feign.SpotifyApiFeignClient;
import com.music.transfer.external.manager.feign.SpotifyAuthFeignClient;
import com.music.transfer.external.manager.handler.ExternalServiceAuthorizer;
import com.music.transfer.external.manager.handler.ExternalServiceHandler;
import com.music.transfer.external.manager.mapper.SpotifyUserMapper;
import com.music.transfer.external.manager.repository.ExternalServiceTokenRepository;
import com.music.transfer.external.manager.repository.SpotifyUserRepository;
import com.music.transfer.external.manager.util.CodeChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Repository
@RequiredArgsConstructor
public class SpotifyServiceHandlerImpl implements ExternalServiceHandler, ExternalServiceAuthorizer {

    private final ExternalServiceProperties externalServiceProperties;

    private final ExternalServiceTokenRepository externalServiceTokenRepository;

    private final SpotifyUserRepository spotifyUserRepository;

    private final SpotifyAuthFeignClient spotifyAuthFeignClient;

    private final SpotifyApiFeignClient spotifyApiFeignClient;

    private final CodeChallengeService codeChallengeService;

    private final SpotifyUserMapper spotifyUserMapper;

    @Override
    public ExternalServiceType getType() {
        return ExternalServiceType.SPOTIFY;
    }

    @Override
    public void createPlaylist(ExternalServiceTokenDto token, String playlistName) {

    }

    @Override
    public PlaylistDto getPlaylist(ExternalServiceTokenDto token, String playlistName) {
        return null;
    }

    @Override
    public FoundTrackDto findSong(TrackDto trackDto) {
        return null;
    }

    @Override
    public void addSongToPlaylist(ExternalServiceTokenDto token, String playlistName, TrackDto song) {

    }

    @Override
    @SneakyThrows
    public String prepare(@NotNull final Long userId) {
        final var spotifyProperties = externalServiceProperties.getSpotify();
        final String state = codeChallengeService.generateRandomString();
        final String codeVerifier = codeChallengeService.generateRandomString();
        final var token = ExternalServiceToken.builder()
                .state(state)
                .verifier(codeVerifier)
                .userId(userId)
                .build();
        externalServiceTokenRepository.save(token);
        final String codeChallenge = codeChallengeService.generateCodeChallenge(codeVerifier);

        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(spotifyProperties.getUrl())
                .path("authorize")
                .queryParam("redirect_uri", spotifyProperties.getRedirectUrl())
                .queryParam("scope", spotifyProperties.getScope())
                .queryParam("response_type", spotifyProperties.getResponseType())
                .queryParam("client_id", spotifyProperties.getClientId())
                .queryParam("code_challenge_method", "S256")
                .queryParam("code_challenge", codeChallenge)
                .queryParam("state", state)
                .build()
                .toUri()
                .toURL()
                .toString();
    }

    @Override
    public String confirm(@NotNull final String code, @NotNull final String state) {
        final var spotifyProperties = externalServiceProperties.getSpotify();
        var token = externalServiceTokenRepository.findByState(state);
        var request = RequestSpotifyTokenDto.builder()
                .grantType("authorization_code")
                .clientId(spotifyProperties.getClientId())
                .code(code)
                .redirectUri(spotifyProperties.getRedirectUrl())
                .codeVerifier(token.getVerifier())
                .build();
        var response = spotifyAuthFeignClient.getToken(request);
        token.setAccessToken(response.getAccessToken());
        token.setLifetime(response.getExpiresIn());
        token.setLastUpdate(LocalDateTime.now());
        token.setRefreshToken(response.getRefreshToken());
        externalServiceTokenRepository.save(token);
        var profileResponse = spotifyApiFeignClient.getProfile(token.getAccessToken());
        var profile = spotifyUserMapper.dtoToEntity(profileResponse);
        spotifyUserRepository.save(profile);
        return token.getAccessToken();
    }

    @Override
    public String refreshToken(Long userId) {
        var token = externalServiceTokenRepository.findByUserId(userId);
        final var spotifyProperties = externalServiceProperties.getSpotify();
        if (token.getLastUpdate()
                .plus(token.getLifetime(), ChronoUnit.SECONDS)
                .minus(spotifyProperties.getTokenRefreshOffset(), ChronoUnit.MILLIS)
                .isBefore(LocalDateTime.now())) {
            return token.getAccessToken();
        }
        var request = RequestRefreshSpotifyTokenDto.builder()
                .grantType("refresh_token")
                .refreshToken(token.getRefreshToken())
                .clientId(spotifyProperties.getClientId())
                .build();
        var response = spotifyAuthFeignClient.refreshToken(request);
        token.setAccessToken(response.getAccessToken());
        token.setLifetime(response.getExpiresIn());
        token.setLastUpdate(LocalDateTime.now());
        token.setRefreshToken(response.getRefreshToken());
        externalServiceTokenRepository.save(token);
        return token.getAccessToken();
    }

}
