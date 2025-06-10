package com.pramu_k.easybook.repository;

import com.pramu_k.easybook.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
    Optional<Passenger> findByEmail(String email);
}
