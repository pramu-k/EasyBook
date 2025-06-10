package com.pramu_k.easybook.service;

import com.pramu_k.easybook.model.*;
import com.pramu_k.easybook.repository.BookingRepository;
import com.pramu_k.easybook.repository.FlightRepository;
import com.pramu_k.easybook.repository.FlightSeatRepository;
import com.pramu_k.easybook.repository.PassengerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final FlightSeatRepository flightSeatRepository;

    public BookingService(FlightRepository flightRepository, BookingRepository bookingRepository, PassengerRepository passengerRepository, FlightSeatRepository flightSeatRepository) {
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
        this.flightSeatRepository = flightSeatRepository;
    }

    @Transactional
    public Booking createBooking(BookingFormDto form) {
        // --- 1. Input Validation ---
        if (form.getSelectedSeatIds() == null || form.getSelectedSeatIds().isEmpty()) {
            throw new IllegalArgumentException("You must select at least one seat.");
        }

        // --- 2. Find or Create the Passenger ---
        // Check if a passenger with this email already exists to avoid duplicates.
        Optional<Passenger> existingPassenger = passengerRepository.findByEmail(form.getPassengerEmail());

        Passenger passenger = existingPassenger.orElseGet(() -> {
            Passenger newPassenger = new Passenger();
            newPassenger.setPassengerName(form.getPassengerName());
            newPassenger.setEmail(form.getPassengerEmail());
            newPassenger.setPhoneNumber(form.getPassengerPhone()); // Assuming Passenger entity has this field
            return passengerRepository.save(newPassenger);
        });

        // --- 3. Fetch the Flight ---
        Flight flight = flightRepository.findById(form.getFlightId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Flight ID: " + form.getFlightId()));

        // --- 4. Create and Save the Initial Booking record ---
        Booking booking = new Booking();
        booking.setPassenger(passenger);
        booking.setFlight(flight);
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.CONFIRMED); // Or PENDING_PAYMENT if you have that flow
        booking.setBookingReference(UUID.randomUUID().toString().toUpperCase().substring(0, 8));

        // Save it now to get an ID for the FlightSeats to reference
        Booking savedBooking = bookingRepository.save(booking);

        // --- 5. Find and Update the Selected Seats ---
        List<FlightSeat> selectedSeats = flightSeatRepository.findAllById(form.getSelectedSeatIds());

        for (FlightSeat seat : selectedSeats) {
            // A crucial check to prevent double-booking in a race condition
            if (!seat.isAvailable() || !seat.getFlight().getId().equals(flight.getId())) {
                throw new IllegalStateException("Seat " + seat.getSeat().getSeatNumber() + " is no longer available or does not belong to this flight.");
            }
            seat.setAvailable(false);
            seat.setBooking(savedBooking); // Link the seat to the final booking
            seat.setPassenger(passenger);  // Also link the specific seat to the passenger
        }

        // Save all updated seats in a batch operation
        flightSeatRepository.saveAll(selectedSeats);

        return savedBooking;
    }
}
