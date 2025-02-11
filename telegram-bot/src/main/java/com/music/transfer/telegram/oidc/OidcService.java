package com.music.transfer.telegram.oidc;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.music.transfer.telegram.config.properties.OidcProperties;
import com.music.transfer.telegram.oidc.entity.TelegramUser;
import com.music.transfer.telegram.oidc.repository.TelegramRepository;
import com.music.transfer.util.CodeChallengeUtil;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpiringMap;
import net.jodah.expiringmap.ExpiringValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class OidcService {

    private final OAuth20Service oAuthService;

    private final TelegramRepository telegramRepository;

    private final OidcProperties oidcProperties;

    private final ExpiringMap<Long, OAuth2AccessToken> accessTokenMap = ExpiringMap.builder()
            .variableExpiration()
            .expirationListener((id, t) -> log.debug("Access token expired for user id = {}", id))
            .expiringEntryLoader(this::refreshToken)
            .build();

    @Transactional
    public Boolean isAuthenticated(@NotNull Long userId) {
        final var user = telegramRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getState() != null;
        } else {
            createNewUser(userId);
            return false;
        }
    }

    @NotNull
    public String start(@NotNull Long userId) {
        final var state = CodeChallengeUtil.generateRandomString();
        telegramRepository
                .findById(userId)
                .ifPresent(existUser -> updateUser(existUser, state));
        return oAuthService.getAuthorizationUrl(state);
    }

    @Nullable
    @SneakyThrows
    public OAuth2AccessToken finish(@NotNull String state,
                                    @NotNull String code) {
        final var userOptional = telegramRepository.findByState(state);
        if (userOptional.isPresent()) {
            final var user = userOptional.get();
            final var token = oAuthService.getAccessToken(code);
            accessTokenMap.put(user.getId(), token, token.getExpiresIn(), TimeUnit.SECONDS);
            user.setRefreshToken(token.getRefreshToken());
            return token;
        }
        return null;
    }

    @Transactional
    public void updateUser(@NotNull TelegramUser user,
                           @NotNull String state) {
        user.setState(state);
        telegramRepository.save(user);
    }

    @Transactional
    public void createNewUser(@NotNull Long userId) {
        final var newUser = new TelegramUser();
        newUser.setId(userId);
        telegramRepository.save(newUser);
    }

    @SneakyThrows
    @Transactional
    @Nullable
    public ExpiringValue<OAuth2AccessToken> refreshToken(@NotNull Long userId) {
        log.debug("Start refreshing token for user id = {}", userId);
        final var userOptional = telegramRepository.findById(userId);
        if (userOptional.isPresent()) {
            final var user = userOptional.get();
            final var token = oAuthService.refreshAccessToken(user.getRefreshToken());
            return new ExpiringValue<>(token, token.getExpiresIn(), TimeUnit.SECONDS);
        }
        return null;
    }

}
