package com.pramu_k.easybook.repository;

import com.pramu_k.easybook.model.Flight;
import com.pramu_k.easybook.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
