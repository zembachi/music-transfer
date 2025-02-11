package com.music.transfer.thymeleaf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("api/spotify")
@RequiredArgsConstructor
public class SpotifyController {

    @GetMapping("main")
    public String isUserAuthenticated(Principal principal, Model model) {

//        UriComponentsBuilder.newInstance()
//                .scheme("https")
//                .host(spotifyProperties.getUrl())
//                .path("authorize")
//                .queryParam("scope",spotifyProperties.getScope())
//                .queryParam("response_type",spotifyProperties.getResponseType())
//                .queryParam("client_id",spotifyProperties.getClientId())
//                .queryParam("code_challenge_method","S256")
//                .queryParam("code_challenge",codeChallenge)
//                .queryParam("state",state)
//                .build()
//                .toUri()
//                .toURL()
//                .toString();
        return "main";
    }


}
