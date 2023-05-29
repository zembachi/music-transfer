package com.music.transfer.external.manager.dto;

import feign.form.FormProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestSpotifyTokenDto {

    @FormProperty("grant_type")
    private String grantType;

    private String code;

    @FormProperty("redirect_uri")
    private String redirectUri;

    @FormProperty("client_id")
    private String clientId;

    @FormProperty("code_verifier")
    private String codeVerifier;

}
