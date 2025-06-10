package com.pramu_k.easybook.model;

public class SeatConfiguration {
    private int businessRows;
    private int economyRows;
    private int firstRows;
    private int seatsPerRow;
    private int totalSeats;

    public int getFirstRows() {
        return firstRows;
    }

    public void setFirstRows(int firstRows) {
        this.firstRows = firstRows;
    }

    public int getBusinessRows() {
        return businessRows;
    }

    public void setBusinessRows(int businessRows) {
        this.businessRows = businessRows;
    }

    public int getEconomyRows() {
        return economyRows;
    }

    public void setEconomyRows(int economyRows) {
        this.economyRows = economyRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
}
