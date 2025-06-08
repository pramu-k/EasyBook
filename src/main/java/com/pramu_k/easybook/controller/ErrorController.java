package com.pramu_k.easybook.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @GetMapping("/error")
    public String handleError() {
        return "error"; // custom error.html
    }
}
