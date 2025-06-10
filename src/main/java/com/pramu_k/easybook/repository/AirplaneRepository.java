package com.pramu_k.easybook.repository;

import com.pramu_k.easybook.model.Airplane;
import com.pramu_k.easybook.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    List<Airplane> findAllByOrderByRegistrationNumberAsc();
}
