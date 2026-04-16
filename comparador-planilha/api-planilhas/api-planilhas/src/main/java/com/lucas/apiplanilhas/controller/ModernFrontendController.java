package com.lucas.apiplanilhas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModernFrontendController {

    @GetMapping("/nova-ui")
    public String exibirNovaInterface() {
        return "forward:/nova-ui/index.html";
    }
}
