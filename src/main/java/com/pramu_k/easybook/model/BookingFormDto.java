package com.pramu_k.easybook.model;

import java.util.List;

public class BookingFormDto {
    private Long flightId;
    private String passengerName;
    private String passengerEmail;
    private String passengerPhone; // Assuming passenger has a phone number

    // This will hold the IDs of the checkboxes the user ticked
    private List<Long> selectedSeatIds;

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public List<Long> getSelectedSeatIds() {
        return selectedSeatIds;
    }

    public void setSelectedSeatIds(List<Long> selectedSeatIds) {
        this.selectedSeatIds = selectedSeatIds;
    }
}
