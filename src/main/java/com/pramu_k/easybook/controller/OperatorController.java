package com.pramu_k.easybook.controller;

import com.pramu_k.easybook.model.Airplane;
import com.pramu_k.easybook.repository.AirplaneRepository;
import com.pramu_k.easybook.repository.FlightRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/operator")
public class OperatorController {
    private final AirplaneRepository airplaneRepository;
    private final FlightRepository flightRepository;

    public OperatorController(AirplaneRepository airplaneRepository,FlightRepository flightRepository) {
        this.airplaneRepository = airplaneRepository;
        this.flightRepository = flightRepository;
    }

    @GetMapping("/operator-panel")
    public String showOperatorPanel(Model model) {
        model.addAttribute("airplanes", airplaneRepository.findAll());
        model.addAttribute("flights", flightRepository.findAll());
        return "operator/operator-panel"; // points to operator/operator-panel.html
    }
}
