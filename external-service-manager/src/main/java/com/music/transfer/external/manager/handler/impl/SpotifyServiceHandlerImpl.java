package com.music.transfer.external.manager.handler.impl;

import com.music.transfer.dto.ExternalServiceTokenDto;
import com.music.transfer.dto.ExternalServiceType;
import com.music.transfer.dto.FoundSongDto;
import com.music.transfer.dto.PlaylistDto;
import com.music.transfer.external.manager.dto.RequestSpotifyTokenDto;
import com.music.transfer.dto.SongDto;
import com.music.transfer.external.manager.config.properties.ExternalServiceProperties;
import com.music.transfer.external.manager.entity.ExternalServiceToken;
import com.music.transfer.external.manager.feign.SpotifyFeignClient;
import com.music.transfer.external.manager.handler.ExternalServiceAuthorizer;
import com.music.transfer.external.manager.handler.ExternalServiceHandler;
import com.music.transfer.external.manager.repository.ExternalServiceTokenRepository;
import com.music.transfer.external.manager.util.CodeChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;

@Repository
@RequiredArgsConstructor
public class SpotifyServiceHandlerImpl implements ExternalServiceHandler, ExternalServiceAuthorizer {

    private final ExternalServiceProperties externalServiceProperties;

    private final ExternalServiceTokenRepository externalServiceTokenRepository;

    private final SpotifyFeignClient spotifyFeignClient;

    private final CodeChallengeService codeChallengeService;

    @Override
    public ExternalServiceType getType() {
        return ExternalServiceType.SPOTIFY;
    }

    @Override
    public ExternalServiceTokenDto loginToExternalService(String username, String password, ExternalServiceType type) {
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
    public FoundSongDto findSong(SongDto songDto) {
        return null;
    }

    @Override
    public void addSongToPlaylist(ExternalServiceTokenDto token, String playlistName, SongDto song) {

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
        var response = spotifyFeignClient.getToken(request);
        token.setToken(response.getAccessToken());
        externalServiceTokenRepository.save(token);
        return token.getToken();
    }

}
