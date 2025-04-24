package com.restaurant.booking.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.booking.model.Reservation;
import com.restaurant.booking.model.ReserveTableCommand;
import com.restaurant.booking.model.Table;
import com.restaurant.booking.service.BookingInvoker;
import com.restaurant.booking.service.BookingService;

@RestController
@RequestMapping("/api")
public class BookingController {

    private final BookingService bookingService;
    private final BookingInvoker bookingInvoker;

    @Autowired
    public BookingController(BookingService bookingService, BookingInvoker bookingInvoker) {
        this.bookingService = bookingService;
        this.bookingInvoker = bookingInvoker;
    }

    @PostMapping("/reserve")
    public String reserveTable(@RequestParam String customerName,
                               @RequestParam int tableId,
                               @RequestParam String date,
                               @RequestParam String arrivalTime,
                               @RequestParam String departureTime) {

        ReserveTableCommand reserveTableCommand = new ReserveTableCommand(
                bookingService,
                customerName,
                tableId,
                date,
                arrivalTime,
                departureTime
        );

        bookingInvoker.setCommand(reserveTableCommand);
        return bookingInvoker.executeCommand();
    }

    @GetMapping("/tables")
    public List<Table> getTables() {
        return bookingService.getTables();
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return bookingService.getReservations();
    }

    @DeleteMapping("/reservation")
    public String deleteReservation(@RequestParam String customerName,
                                    @RequestParam String date,
                                    @RequestParam String arrivalTime,
                                    @RequestParam int tableId) {
        return bookingService.deleteReservation(customerName, date, arrivalTime, tableId);
    }

    @GetMapping("/reservations/search")
    public List<Reservation> searchReservations(@RequestParam String name) {
        String normalized = name.trim().toLowerCase();
        return bookingService.getReservations().stream()
                .filter(r -> r.getCustomerName().toLowerCase().contains(normalized))
                .collect(Collectors.toList());
    }
}
