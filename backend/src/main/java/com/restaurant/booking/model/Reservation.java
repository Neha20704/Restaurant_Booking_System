package com.restaurant.booking.model; // Defines the package this class belongs to

// Represents a reservation made by a customer
public class Reservation {
    private String customerName;     // Name of the customer making the reservation
    private String date;             // Date of the reservation (e.g., "2025-04-20")
    private String arrivalTime;      // Time the customer is expected to arrive (e.g., "18:00")
    private String departureTime;    // Time the customer is expected to leave (e.g., "20:00")
    private Table table;             // The table assigned to this reservation

    // Constructor to initialize all reservation details
    public Reservation(String customerName, String date, String arrivalTime, String departureTime, Table table) {
        this.customerName = customerName;
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.table = table;
    }

    // Getter method to retrieve the customer's name
    public String getCustomerName() {
        return customerName;
    }

    // Getter method to retrieve the reservation date
    public String getDate() {
        return date;
    }

    // Getter method to retrieve the arrival time
    public String getArrivalTime() {
        return arrivalTime;
    }

    // Getter method to retrieve the departure time
    public String getDepartureTime() {
        return departureTime;
    }

    // Getter method to retrieve the table assigned to this reservation
    public Table getTable() {
        return table;
    }
}
