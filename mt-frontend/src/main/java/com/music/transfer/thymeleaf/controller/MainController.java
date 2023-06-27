package com.music.transfer.thymeleaf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("api")
@RequiredArgsConstructor
public class MainController {

    @GetMapping("main")
    public String isUserAuthenticated(Principal principal, Model model) {
        return "main";
    }

}