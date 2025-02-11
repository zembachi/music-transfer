package com.music.transfer.external.manager.controller;

import com.music.transfer.dto.ResponseGetAuthenticatedServiceInfoDto;
import com.music.transfer.external.manager.handler.impl.ExternalServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/main")
@RequiredArgsConstructor
public class MainController {

    private final ExternalServiceManager externalServiceManager;

    @GetMapping(value = "auth/info")
    @PreAuthorize("hasAuthority('custom')")
    @Validated
    public ResponseEntity<List<ResponseGetAuthenticatedServiceInfoDto>> isUserAuthenticated() {
        List<ResponseGetAuthenticatedServiceInfoDto> responseBody = externalServiceManager.getAuthInfo();
        return ResponseEntity.ok().body(responseBody);
    }

}
