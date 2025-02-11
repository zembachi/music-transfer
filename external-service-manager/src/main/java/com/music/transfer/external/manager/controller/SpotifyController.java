package com.music.transfer.external.manager.controller;

import com.music.transfer.external.manager.dto.PrepareSpotifyAuthDto;
import com.music.transfer.external.manager.handler.SpotifyService;
import com.music.transfer.external.manager.request.context.AppRequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("spotify")
@RequiredArgsConstructor
public class SpotifyController {

    private final SpotifyService spotifyService;

    private final AppRequestContextHolder appRequestContextHolder;

    @GetMapping("prepare")
    @Validated
    public ResponseEntity<String> prepareLogin() {
        final var userId = appRequestContextHolder.get().appUser().getId();
        final var urlToRedirect = spotifyService.prepare(userId);
        return ResponseEntity.ok().body(urlToRedirect);
    }

    @GetMapping("confirm")
    @Validated
    public ResponseEntity<String> prepareLogin(@NotBlank String code,
                                               @NotBlank String state,
                                               @NotNull String redirectUrl) {
//        String accessToken = spotifyService.confirm(code, state, redirectUrl);
        return ResponseEntity.ok()
                .body(null);
    }

}
