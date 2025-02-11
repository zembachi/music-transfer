package com.music.transfer.external.manager.handler;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

public interface ExternalServiceAuthorizer {

    @NotNull
    String prepare(@NotNull String userId);

    void confirm(@NonNull String code, @NonNull String state);

    @NotNull
    String refreshToken(@NonNull String userId);

    boolean isAuthorized(@NonNull String userId);

    @NonNull
    String urlToRedirect();

}
