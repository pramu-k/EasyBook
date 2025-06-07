package com.pramu_k.easybook.controller;

import com.pramu_k.easybook.model.Flight;
import com.pramu_k.easybook.repository.FlightRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FlightController {
    private FlightRepository flightRepository;
    public FlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @GetMapping("/flights/add")
    public String showAddFlightForm(Model model) {
        model.addAttribute("flight", new Flight());
        return "add-flight";
    }

    @PostMapping("/flights/add")
    public String saveFlight(@ModelAttribute Flight flight) {
        flightRepository.save(flight);
        return "redirect:/";  // or return "flights" to show the updated list
    }

}
