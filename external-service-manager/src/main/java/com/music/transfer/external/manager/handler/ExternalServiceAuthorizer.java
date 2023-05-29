package com.music.transfer.external.manager.handler;

import javax.validation.constraints.NotNull;

public interface ExternalServiceAuthorizer {

    String prepare(@NotNull Long userId);

    String confirm(@NotNull String code, @NotNull String state);

}
