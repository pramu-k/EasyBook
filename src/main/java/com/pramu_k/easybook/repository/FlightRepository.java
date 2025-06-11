package com.pramu_k.easybook.repository;

import com.pramu_k.easybook.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT DISTINCT f FROM Flight f LEFT JOIN FETCH f.flightSeats")
    List<Flight> findAllWithSeats();
}
