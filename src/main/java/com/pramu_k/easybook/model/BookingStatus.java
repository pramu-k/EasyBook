package com.pramu_k.easybook.model;

public enum BookingStatus {
    PENDING,       // Booking created but payment not completed
    CONFIRMED,     // Payment successful, booking confirmed
    CANCELLED,     // Booking cancelled by user
    EXPIRED,       // Booking not confirmed within time limit
    REFUNDED,      // Cancelled booking with refund processed
    FAILED,        // Payment failed
    CHECKED_IN,    // Passenger has checked in
    BOARDED,       // Passenger has boarded the flight
    NO_SHOW        // Passenger didn't show up for flight
}
