package com.pramu_k.easybook.controller;

import com.pramu_k.easybook.model.Airplane;
import com.pramu_k.easybook.service.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/operator/airplanes")
@PreAuthorize("hasAnyRole('OPERATOR', 'ADMIN')")
public class AirplaneController {
    private final AirplaneService airplaneService;

    @Autowired
    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @GetMapping("/new")
    public String showAddAirplaneForm(Model model) {
        model.addAttribute("airplane", new Airplane());
        return "operator/airplanes/add";
    }

    @PostMapping
    public String addAirplane(
            @ModelAttribute Airplane airplane,
            @RequestParam String seatConfiguration,
            RedirectAttributes redirectAttributes) {

        try {
            Airplane savedAirplane = airplaneService.saveAirplaneWithSeats(airplane, seatConfiguration);
            redirectAttributes.addFlashAttribute("success",
                    "Airplane " + savedAirplane.getRegistrationNumber() + " added successfully!");
            return "redirect:/operator/operator-panel";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error adding airplane: " + e.getMessage());
            return "redirect:/operator/airplanes/new";
        }
    }
}
