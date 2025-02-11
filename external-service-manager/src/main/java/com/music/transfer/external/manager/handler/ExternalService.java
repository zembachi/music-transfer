package com.music.transfer.external.manager.handler;

import com.music.transfer.dto.ExternalServiceType;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

public interface ExternalService extends ExternalServiceAuthorizer {

    @NotNull
    ExternalServiceType getType();

    @NotNull
    String getName();

    @NotNull
    String getRedirectUrl();
}
