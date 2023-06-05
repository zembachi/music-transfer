package com.music.transfer.external.manager.dto;

import feign.form.FormProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestRefreshSpotifyTokenDto {

    @FormProperty("grant_type")
    private String grantType;

    @FormProperty("refresh_token")
    private String refreshToken;

    @FormProperty("client_id")
    private String clientId;

}
