package com.music.transfer.telegram;

import com.auth0.jwt.JWT;
import com.github.scribejava.apis.openid.OpenIdOAuth2AccessToken;
import lombok.Data;

@Data
public class UserInfo {

    private final String subject;

    private final String preferredUsername;

    static UserInfo of(OpenIdOAuth2AccessToken token) {
        var jwt = JWT.decode(token.getOpenIdToken());
        var subject = jwt.getSubject();
        var preferredUsername = jwt.getClaim("preferred_username").asString();

        return new UserInfo(subject, preferredUsername);
    }

}
