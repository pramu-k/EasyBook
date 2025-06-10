package com.pramu_k.easybook.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pramu_k.easybook.model.Airplane;
import com.pramu_k.easybook.model.Seat;
import com.pramu_k.easybook.model.SeatClass;
import com.pramu_k.easybook.model.SeatConfiguration;
import com.pramu_k.easybook.repository.AirplaneRepository;
import com.pramu_k.easybook.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AirplaneService {
    private final AirplaneRepository airplaneRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public AirplaneService(AirplaneRepository airplaneRepository, SeatRepository seatRepository) {
        this.airplaneRepository = airplaneRepository;
        this.seatRepository = seatRepository;
    }

    public Airplane saveAirplaneWithSeats(Airplane airplane, String seatConfigurationJson) {
        // Parse seat configuration
        SeatConfiguration config = parseSeatConfiguration(seatConfigurationJson);

        // Validate total seats matches configuration
        if (airplane.getSeatCapacity() != config.getTotalSeats()) {
            throw new IllegalArgumentException(
                    "Configured seats (" + config.getTotalSeats() +
                            ") don't match airplane capacity (" + airplane.getSeatCapacity() + ")");
        }

        // Save the airplane first
        Airplane savedAirplane = airplaneRepository.save(airplane);

        // Generate and save seats
        List<Seat> seats = generateSeats(savedAirplane, config);
        seatRepository.saveAll(seats);

        return savedAirplane;
    }

    private SeatConfiguration parseSeatConfiguration(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, SeatConfiguration.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid seat configuration", e);
        }
    }

    private List<Seat> generateSeats(Airplane airplane, SeatConfiguration config) {
        List<Seat> seats = new ArrayList<>();
        int seatsPerRow = config.getSeatsPerRow();
        int currentRow = 1;

        // Generate First Class seats
        for (int row = currentRow; row < currentRow + config.getFirstRows(); row++) {
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                seats.add(createSeat(airplane, row, seatNum, SeatClass.FIRST));
            }
        }
        currentRow += config.getFirstRows();

        // Generate Business Class seats
        for (int row = currentRow; row < currentRow + config.getBusinessRows(); row++) {
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                seats.add(createSeat(airplane, row, seatNum, SeatClass.BUSINESS));
            }
        }
        currentRow += config.getBusinessRows();

        // Generate Economy Class seats
        for (int row = currentRow; row < currentRow + config.getEconomyRows(); row++) {
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                seats.add(createSeat(airplane, row, seatNum, SeatClass.ECONOMY));
            }
        }

        return seats;
    }

    private Seat createSeat(Airplane airplane, int row, int seatNum, SeatClass seatClass) {
        char seatLetter = (char) ('A' + seatNum - 1);
        String seatNumber = row + String.valueOf(seatLetter);

        Seat seat = new Seat();
        seat.setSeatNumber(seatNumber);
        seat.setSeatClass(seatClass);
        seat.setAirplane(airplane);

        return seat;
    }
    public List<Airplane> findAllAirplanes() {
        return airplaneRepository.findAllByOrderByRegistrationNumberAsc();
    }
}
