package com.music.transfer.dto;

public record ResponseGetAuthenticatedServiceInfoDto(ExternalServiceType type,
                                                     boolean authenticated,
                                                     String urlToRedirect) {
}