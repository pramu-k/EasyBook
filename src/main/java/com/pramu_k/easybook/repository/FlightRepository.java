package com.pramu_k.easybook.repository;

import com.pramu_k.easybook.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
}
