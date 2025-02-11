package com.music.transfer.util;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TokenUtil {

    private TokenUtil() {
    }

    public static boolean isTokenNotExpired(@NotNull LocalDateTime lastUpdate,
                                            @NotNull Integer lifetime,
                                            @NotNull Long refreshOffset) {
        return lastUpdate
                .plus(lifetime, ChronoUnit.SECONDS)
                .minus(refreshOffset, ChronoUnit.MILLIS)
                .isBefore(LocalDateTime.now());
    }

}
