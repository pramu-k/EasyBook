package com.pramu_k.easybook.controller;

import com.pramu_k.easybook.model.Airplane;
import com.pramu_k.easybook.model.Booking;
import com.pramu_k.easybook.model.Flight;
import com.pramu_k.easybook.repository.AirplaneRepository;
import com.pramu_k.easybook.repository.BookingRepository;
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
    private final BookingRepository bookingRepository;

    public OperatorController(AirplaneRepository airplaneRepository,FlightRepository flightRepository,BookingRepository bookingRepository) {
        this.airplaneRepository = airplaneRepository;
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/operator-panel")
    public String showOperatorPanel(Model model) {
        List<Flight> flights = flightRepository.findAllWithSeats();
        flights.removeIf(flight -> flight.getFlightSeats().stream().count()==0);
        List<Booking> bookings = bookingRepository.findAll();
        model.addAttribute("airplanes", airplaneRepository.findAll());
        model.addAttribute("flights", flights);
        model.addAttribute("airplaneCount", airplaneRepository.count());
        model.addAttribute("flightCount", flightRepository.count());
        model.addAttribute("bookingCount", bookingRepository.count());
        model.addAttribute("recentBookings", bookingRepository.findAll());
        return "operator/operator-panel"; // points to operator/operator-panel.html
    }
}
