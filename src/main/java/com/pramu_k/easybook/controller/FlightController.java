package com.pramu_k.easybook.controller;

import com.pramu_k.easybook.model.Flight;
import com.pramu_k.easybook.repository.FlightRepository;
import com.pramu_k.easybook.service.AirplaneService;
import com.pramu_k.easybook.service.FlightService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FlightController {
    private FlightRepository flightRepository;
    private AirplaneService airplaneService;
    private FlightService flightService;

    public FlightController(FlightRepository flightRepository, AirplaneService airplaneService,FlightService flightService) {
        this.flightRepository = flightRepository;
        this.airplaneService = airplaneService;
        this.flightService = flightService;
    }

    @GetMapping("/operator/flights/add")
    @PreAuthorize("hasAnyRole('OPERATOR', 'ADMIN')")
    public String showAddFlightForm(Model model) {
        model.addAttribute("flight", new Flight());
        model.addAttribute("allAirplanes", airplaneService.findAllAirplanes());
        return "operator/flights/add";
    }

    @PostMapping("/operator/flights/add")
    @PreAuthorize("hasAnyRole('OPERATOR', 'ADMIN')")
    public String saveFlight(@ModelAttribute("flight") Flight flight, RedirectAttributes redirectAttributes) {
        try {
            flightService.saveFlight(flight);
            redirectAttributes.addFlashAttribute("successMessage", "Flight " + flight.getFlightNumber() + " created successfully!");
            // Redirect to a flight list page (assuming one exists)
            return "redirect:/operator/operator-panel";
        } catch (Exception e) {
            // If there's an error (e.g., duplicate flight number), show the form again
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating flight: " + e.getMessage());
            // It's often better to redirect back to the form with the user's input preserved
            return "redirect:/operator/flights/add";
        }
    }
    @GetMapping("/search-fights")
    public String viewAllFlights(Model model) {
        List<Flight> allFlights = flightRepository.findAll();

        model.addAttribute("flights", allFlights);

        return "search-flights";
    }

}
