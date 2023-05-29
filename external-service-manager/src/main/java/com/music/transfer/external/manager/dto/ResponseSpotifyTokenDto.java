package com.music.transfer.external.manager.dto;

import lombok.Data;

@Data
public class ResponseSpotifyTokenDto {

    private String accessToken;

    private String refreshToken;

    private Integer expiresIn;

    private String tokenType;

}
