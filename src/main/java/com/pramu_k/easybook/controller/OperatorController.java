package com.pramu_k.easybook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/operator")
public class OperatorController {
    @GetMapping("/operator-panel")
    public String showOperatorPanel() {
        return "operator/operator-panel"; // points to operator/operator-panel.html
    }
}
