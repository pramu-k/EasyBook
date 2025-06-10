package com.pramu_k.easybook.service;

import com.pramu_k.easybook.model.Flight;
import com.pramu_k.easybook.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight saveFlight(Flight flight) {
        // validation logic, e.g., ensure departure is before arrival
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime())) {
            throw new IllegalStateException("Departure time must be before arrival time.");
        }
        return flightRepository.save(flight);
    }
}
