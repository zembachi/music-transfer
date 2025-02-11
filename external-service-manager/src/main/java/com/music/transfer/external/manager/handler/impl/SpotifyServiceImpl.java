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
import com.music.transfer.external.manager.handler.SpotifyService;
import com.music.transfer.external.manager.mapper.SpotifyUserMapper;
import com.music.transfer.external.manager.repository.ExternalServiceTokenRepository;
import com.music.transfer.external.manager.repository.SpotifyUserRepository;
import com.music.transfer.external.manager.request.context.AppRequestContextHolder;
import com.music.transfer.util.CodeChallengeUtil;
import com.music.transfer.util.TokenUtil;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Repository
@RequiredArgsConstructor
public class SpotifyServiceImpl implements SpotifyService {

    private final ExternalServiceProperties externalServiceProperties;

    private final ExternalServiceTokenRepository externalServiceTokenRepository;

    private final SpotifyUserRepository spotifyUserRepository;

    private final SpotifyAuthFeignClient spotifyAuthFeignClient;

    private final SpotifyApiFeignClient spotifyApiFeignClient;

    private final SpotifyUserMapper spotifyUserMapper;

    private final AppRequestContextHolder appRequestContextHolder;

    @Override
    public ExternalServiceType getType() {
        return ExternalServiceType.SPOTIFY;
    }

    @Override
    public String getName() {
        return externalServiceProperties.getSpotify().getName();
    }

    @Override
    public String getRedirectUrl() {
        return null;
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
    public String prepare(@NotNull final String userId) {
        final var spotifyProperties = externalServiceProperties.getSpotify();
        final String state = CodeChallengeUtil.generateRandomString();
        final String codeVerifier = CodeChallengeUtil.generateRandomString();
        final var token = ExternalServiceToken.builder()
                .state(state)
                .verifier(codeVerifier)
                .userId(userId)
                .build();
        externalServiceTokenRepository.save(token);
        final String codeChallenge = CodeChallengeUtil.generateCodeChallenge(codeVerifier);

        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(spotifyProperties.getUrl())
                .path("authorize")
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
    public void confirm(@NotNull final String code, @NotNull final String state) {

        final var spotifyProperties = externalServiceProperties.getSpotify();
        final var userId = appRequestContextHolder.get().appUser().getId();
        final var token = externalServiceTokenRepository.findByStateAndUserId(state, userId);
        if (token == null) {
            return;
        }
        final var request = RequestSpotifyTokenDto.builder()
                .grantType("authorization_code")
                .clientId(spotifyProperties.getClientId())
                .code(code)
                .redirectUri(spotifyProperties.getUrl())
                .codeVerifier(token.getVerifier())
                .build();
        final var response = spotifyAuthFeignClient.getToken(request);
        token.setAccessToken(response.getAccessToken());
        token.setLifetime(response.getExpiresIn());
        token.setLastUpdate(LocalDateTime.now());
        token.setRefreshToken(response.getRefreshToken());
        externalServiceTokenRepository.save(token);
    }

    @Override
    public String refreshToken(@NonNull final String userId) {
        var token = externalServiceTokenRepository.findByUserId(userId);
        final var spotifyProperties = externalServiceProperties.getSpotify();
        if (isTokenNotExpired(token)) {
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

    @Override
    public boolean isAuthorized(@NonNull final String userId) {
        var token = externalServiceTokenRepository.findByUserId(userId);
        if (token == null) {
            return false;
        }
        refreshToken(userId);
        return true;
    }

    @Override
    public String urlToRedirect() {
        return null;
    }

    @NonNull
    private String getTokenHeaderValue(@NonNull String token) {
        return "Bearer " + token;
    }

    private boolean isTokenNotExpired(@NonNull ExternalServiceToken token) {
        final var spotifyProperties = externalServiceProperties.getSpotify();
        return TokenUtil.isTokenNotExpired(token.getLastUpdate(), token.getLifetime(),
                spotifyProperties.getTokenRefreshOffset());
    }

}
