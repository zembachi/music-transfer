package com.music.transfer.thymeleaf.controller;

import com.music.transfer.dto.ExternalServiceType;
import com.music.transfer.thymeleaf.model.ExternalServiceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("api")
@RequiredArgsConstructor
public class ServiceController {

    @GetMapping("services")
    public String getServices(Model model) {
        ExternalServiceInfo spotifyInfo = ExternalServiceInfo.builder()
                .id(1L)
                .name("Spotify")
                .type(ExternalServiceType.SPOTIFY)
                .status(true)
                .href("/api/main")
                .build();
        ExternalServiceInfo vkInfo = ExternalServiceInfo.builder()
                .id(1L)
                .name("Вконтакте Music")
                .type(ExternalServiceType.VK_MUSIC)
                .href("/api/test")
                .status(false)
                .build();
        List<ExternalServiceInfo> services = new ArrayList<>();
        services.add(spotifyInfo);
        services.add(vkInfo);
        model.addAttribute("services", services);
        return "services";
    }

}
