package com.restaurant.booking.model;

import com.restaurant.booking.service.BookingService;

public class ReserveTableCommand implements Command {

    private final BookingService bookingService;
    private final String customerName;
    private final int tableId;
    private final String date;
    private final String arrivalTime;
    private final String departureTime;

    public ReserveTableCommand(BookingService bookingService, String customerName, int tableId, String date, String arrivalTime, String departureTime) {
        this.bookingService = bookingService;
        this.customerName = customerName;
        this.tableId = tableId;
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    @Override
    public String execute() {
        return bookingService.reserveTable(customerName, tableId, date, arrivalTime, departureTime);
    }
}
