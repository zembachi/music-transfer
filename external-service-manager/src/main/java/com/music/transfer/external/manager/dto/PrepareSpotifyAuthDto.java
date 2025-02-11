package com.music.transfer.external.manager.dto;

public record PrepareSpotifyAuthDto(String url, String scope, String responseType, String clientId,
                                    String codeChallenge, String state) {

}