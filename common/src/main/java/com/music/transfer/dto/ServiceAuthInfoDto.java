package com.music.transfer.dto;

import lombok.Builder;

@Builder
public record ServiceAuthInfoDto(ExternalServiceType type, boolean authenticated) {
}
