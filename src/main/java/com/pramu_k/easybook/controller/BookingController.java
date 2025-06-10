package com.pramu_k.easybook.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingController {
    @GetMapping("/my-bookings")
    public String viewMyBookings(Authentication authentication, Model model) {
        if (authentication == null ||
                authentication.getAuthorities().stream().noneMatch(auth ->
                        auth.getAuthority().equals("ROLE_ADMIN")||

                                //auth.getAuthority().equals("ROLE_OPERATOR") ||
                                auth.getAuthority().equals("ROLE_CUSTOMER")
                )) {

            return "redirect:/access-denied";
        }

        // Add user's bookings to the model here
        // model.addAttribute("bookings", bookingService.getBookingsForUser(authentication.getName()));

        return "my-bookings"; // Thymeleaf page: my-bookings.html
    }
}
