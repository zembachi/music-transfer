package com.music.transfer.external.manager.controller;

import com.music.transfer.dto.ExternalServiceType;
import com.music.transfer.external.manager.handler.ExternalServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("spotify")
@RequiredArgsConstructor
public class SpotifyController {

    private final ExternalServiceHandler externalServiceHandler;

    @GetMapping("prepare/user/{userId}")
    @Validated
    public ResponseEntity<String> prepareLogin(@PathVariable @Positive Long userId) {
        String urlToRedirect = externalServiceHandler.prepare(userId);
        return ResponseEntity.ok()
                .body(urlToRedirect);
    }

    @GetMapping("confirm")
    @Validated
    public ResponseEntity<String> prepareLogin(@NotBlank String code, @NotBlank String state) {
        String accessToken = externalServiceHandler.confirm(code, state);
        return ResponseEntity.ok()
                .body(accessToken);
    }
}
