package com.pramu_k.easybook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/booking-success")
    public String showBookingSuccessPage() {
        return "booking-success";
    }
}
