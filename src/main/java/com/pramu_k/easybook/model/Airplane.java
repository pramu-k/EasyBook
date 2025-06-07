package com.pramu_k.easybook.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registrationNumber;
    private String model;
    private String manufacturer;
    private int seatCapacity;

    @OneToMany(mappedBy = "airplane")
    private List<Flight> flights;

    // Getters and setters
}

