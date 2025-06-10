package com.pramu_k.easybook.repository;

import com.pramu_k.easybook.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
