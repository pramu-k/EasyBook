package com.pramu_k.easybook.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;  // Add unique flight number
    private String airline;
    private String origin;
    private String destination;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private double basePrice;
    @ManyToOne(fetch = FetchType.LAZY) // LAZY is good for performance
    @JoinColumn(name = "airplane_id", nullable = false) // This creates the foreign key column
    private Airplane airplane;
    @OneToMany(mappedBy = "flight")
    private List<FlightSeat> flightSeats;
    @Transient
    private Integer totalSeats;

    @Transient
    private Integer availableSeats;

    // --- GETTERS AND SETTERS FOR THE NEW FIELDS ---
    // The calculation logic goes inside the getters.

    public Integer getTotalSeats() {
        if (this.flightSeats == null) {
            return 0; // Or handle as an error if seats should always be loaded
        }
        return this.flightSeats.size();
    }

    public Integer getAvailableSeats() {
        if (this.flightSeats == null) {
            return 0;
        }
        // Use Java Streams to filter the list and count the available ones.
        return (int) this.flightSeats.stream()
                .filter(FlightSeat::isAvailable) // Method reference for s -> s.isAvailable()
                .count();
    }


    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public List<FlightSeat> getFlightSeats() {
        return flightSeats;
    }

    public void setFlightSeats(List<FlightSeat> flightSeats) {
        this.flightSeats = flightSeats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

}
