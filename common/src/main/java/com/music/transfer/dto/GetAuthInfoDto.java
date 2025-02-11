package com.music.transfer.dto;

public record GetAuthInfoDto(ExternalServiceType type,
                             boolean status,
                             String href) {
}
