package com.pramu_k.easybook.repository;

import com.pramu_k.easybook.model.FlightSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightSeatRepository extends JpaRepository<FlightSeat, Long> {
    List<FlightSeat> findByFlightIdAndAvailableTrue(Long flightId);

}
