package com.music.transfer.external.manager.controller;

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
@RequestMapping("main")
@RequiredArgsConstructor
public class MainController {

    private final ExternalServiceHandler externalServiceHandler;

    @GetMapping("authenticated")
    @Validated
    public ResponseEntity<String> isUserAuthenticated(@PathVariable @Positive Long userId) {
        String urlToRedirect = externalServiceHandler.prepare(userId);
        return ResponseEntity.ok()
                .body(urlToRedirect);
    }

}