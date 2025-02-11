package com.music.transfer.thymeleaf.controller;

import com.music.transfer.dto.ExternalServiceType;
import com.music.transfer.thymeleaf.feign.AppResourceServerFeignClient;
import com.music.transfer.thymeleaf.model.ExternalServiceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    private final AppResourceServerFeignClient appResourceServerFeignClient;

    private final RabbitTemplate rabbitTemplate;

    @GetMapping("services")
    public String getServices(Model model, Principal principal) {
        rabbitTemplate.convertAndSend("testExchange", "test.key", "test");
        final var response = appResourceServerFeignClient.getAuthenticatedInfo().get(0);
        ExternalServiceInfo spotifyInfo = ExternalServiceInfo.builder()
                .id(1L)
                .name(response.type().name())
                .type(response.type())
                .status(response.authenticated())
                .href(response.urlToRedirect())
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
