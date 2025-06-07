package com.pramu_k.easybook.model;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long passengerID;
    public String passengerName;
    public LocalDate passengerDOB;
    private String gender;
    private String passportNumber;
    private String nationality;
    private String email;
    private String phoneNumber;
    @OneToMany(mappedBy = "passenger")
    private List<Seat> seats = new ArrayList<>();

}
