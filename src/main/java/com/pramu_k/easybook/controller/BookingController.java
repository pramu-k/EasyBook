package com.pramu_k.easybook.controller;

import com.pramu_k.easybook.model.Booking;
import com.pramu_k.easybook.model.BookingFormDto;
import com.pramu_k.easybook.model.Flight;
import com.pramu_k.easybook.model.FlightSeat;
import com.pramu_k.easybook.repository.BookingRepository;
import com.pramu_k.easybook.repository.FlightRepository;
import com.pramu_k.easybook.repository.FlightSeatRepository;
import com.pramu_k.easybook.service.BookingService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class BookingController {
    private final FlightRepository flightRepository;
    private final FlightSeatRepository flightSeatRepository;
    private final BookingService bookingService;

    public BookingController(FlightRepository flightRepository,FlightSeatRepository flightSeatRepository,BookingService bookingService) {
        this.flightRepository = flightRepository;
        this.flightSeatRepository = flightSeatRepository;
        this.bookingService = bookingService;
    }

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
    @GetMapping("/booking/flight/{id}")
    public String showBookingPage(@PathVariable("id") Long id, Model model) {
        // TODO:
        // 1. Fetch the flight details using the 'id'.
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid flight Id:" + id));
        // 2. Fetch only the AVAILABLE seats for this specific flight
        List<FlightSeat> availableSeats = flightSeatRepository.findByFlightIdAndAvailableTrue(id);

        // 3. Create a new, empty DTO for our form
        BookingFormDto bookingForm = new BookingFormDto();
        bookingForm.setFlightId(flight.getId()); // Pre-populate the flight ID in the form

        // 4. Add all necessary objects to the model for the view
        model.addAttribute("flight", flight);
        model.addAttribute("availableSeats", availableSeats);
        model.addAttribute("bookingForm", bookingForm); // This is for th:object
        return "book-flight";
    }
    @PostMapping("/bookings/create")
    public String processBooking(@ModelAttribute("bookingForm") BookingFormDto bookingForm,
                                 RedirectAttributes redirectAttributes) {
        try {
            // Delegate all the complex logic to the service layer
            Booking newBooking = bookingService.createBooking(bookingForm);

            // Add a success message to be displayed on the next page
            redirectAttributes.addFlashAttribute("successMessage",
                    "Booking successful! Your reference is: " + newBooking.getBookingReference());

            // Redirect to a confirmation/success page
            return "redirect:/booking-success"; // You will need to create a GET mapping and a page for this

        } catch (IllegalArgumentException | IllegalStateException e) {
            // If there's a business logic error (e.g., no seats selected, seat taken)
            // redirect back to the form with a specific error message.
            redirectAttributes.addFlashAttribute("errorMessage", "Booking failed: " + e.getMessage());

            // Redirect back to the SAME booking page
            return "redirect:/booking/flight/" + bookingForm.getFlightId();
        } catch (Exception e) {
            // For any other unexpected errors
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            return "redirect:/booking/flight/" + bookingForm.getFlightId();
        }
    }
}
