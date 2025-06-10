package com.pramu_k.easybook.service;

import com.pramu_k.easybook.model.Airplane;
import com.pramu_k.easybook.model.Flight;
import com.pramu_k.easybook.model.FlightSeat;
import com.pramu_k.easybook.model.Seat;
import com.pramu_k.easybook.repository.AirplaneRepository;
import com.pramu_k.easybook.repository.FlightRepository;
import com.pramu_k.easybook.repository.FlightSeatRepository;
import com.pramu_k.easybook.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;
    private final FlightSeatRepository flightSeatRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository,SeatRepository seatRepository,FlightSeatRepository flightSeatRepository) {
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
        this.flightSeatRepository = flightSeatRepository;
    }
    @Transactional
    public Flight saveFlight(Flight flight) {
        // validation logic, e.g., ensure departure is before arrival
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime())) {
            throw new IllegalStateException("Departure time must be before arrival time.");
        }
        Flight savedFlight = flightRepository.save(flight);

        // 2. Get the Airplane associated with this flight. It should be populated from the form.
        Airplane airplane = savedFlight.getAirplane();
        if (airplane == null || airplane.getId() == null) {
            throw new IllegalStateException("An airplane must be assigned to the flight before saving.");
        }
        List<Seat> seatsForThisAirplane = seatRepository.findByAirplaneId(airplane.getId());

        // 3. Prepare a list to hold all the new FlightSeat entities.
        List<FlightSeat> flightSeatsToCreate = new ArrayList<>();

        // 4. Loop through every Seat belonging to the assigned Airplane.
        for (Seat seat : seatsForThisAirplane) {
            FlightSeat flightSeat = new FlightSeat();
            flightSeat.setFlight(savedFlight);
            flightSeat.setSeat(seat);
            flightSeat.setAvailable(true);
            flightSeatsToCreate.add(flightSeat);
        }

        // 5. Save all the new FlightSeat records to the database in a single, efficient batch operation.
        flightSeatRepository.saveAll(flightSeatsToCreate);

        return savedFlight;
    }
}
