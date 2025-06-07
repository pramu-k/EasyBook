package com.pramu_k.easybook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AuthController {
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // This should match login.html in src/main/resources/templates
    }
}
