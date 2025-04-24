package com.restaurant.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.booking.model.Reservation;
import com.restaurant.booking.model.ReservationDocument;
import com.restaurant.booking.model.Table;
import com.restaurant.booking.model.TableFactory;
import com.restaurant.booking.repository.ReservationRepository;

import jakarta.annotation.PostConstruct;

@Service
public class BookingService {

    @Autowired
    private ReservationRepository reservationRepository;

    private final List<Reservation> reservations = new ArrayList<>();
    private final List<Table> tables = new ArrayList<>();

    public BookingService() {
         // Add tables to match frontend --Factory Pattern
        TableFactory tableFactory = new TableFactory();
        int idCounter = 1;

        for (int i = 0; i < 3; i++) tables.add(tableFactory.createTable(idCounter++, 2));  // Small
        for (int i = 0; i < 4; i++) tables.add(tableFactory.createTable(idCounter++, 4));  // Medium
        for (int i = 0; i < 3; i++) tables.add(tableFactory.createTable(idCounter++, 6));  // Large
        for (int i = 0; i < 2; i++) tables.add(tableFactory.createTable(idCounter++, 8));  // ExtraLarge

        tables.add(tableFactory.createTable(idCounter++, 10));  // Fallback/unsupported size test
        tables.add(tableFactory.createTable(idCounter++, 4));   // Medium again
        tables.add(tableFactory.createTable(idCounter++, 6));   // Large again
    }

    //Adapter Pattern adapting ReservationDocument from MongoDB
    @PostConstruct
        public void loadReservationsFromMongoDB() {
            List<ReservationDocument> mongoReservations = reservationRepository.findAll();
            TableFactory tableFactory = new TableFactory();
            
            for (ReservationDocument doc : mongoReservations) {
                // Debugging: Print the values to make sure tableSize is present and correct
                System.out.println("Loading reservation with TableId: " + doc.getTableId() + ", TableSize: " + doc.getTableSize());

                // Ensure tableSize is valid before creating Table
                if (doc.getTableSize() <= 0) {
                    System.err.println("Invalid table size " + doc.getTableSize() + " for reservation " + doc.getId());
                    continue; // Skip invalid reservation
                }

                // Use TableFactory to create the correct Table based on size
                try {
                    Table table = tableFactory.createTable(doc.getTableId(), doc.getTableSize());
                    reservations.add(new Reservation(
                            doc.getCustomerName(),
                            doc.getDate(),
                            doc.getArrivalTime(),
                            doc.getDepartureTime(),
                            table));  // Pass the correct Table object
                } catch (IllegalArgumentException e) {
                    System.err.println("Error creating table for reservation " + doc.getId() + ": " + e.getMessage());
                }
            }
        }

    // Command Pattern: Encapsulates the reservation request as a command
    public String reserveTable(String customerName, int tableId, String date, String arrivalTime, String departureTime) {
        Optional<Table> table = tables.stream()
                .filter(t -> t.getId() == tableId)
                .findFirst();

        if (table.isPresent()) {
            boolean isAlreadyBooked = reservations.stream()
                    .anyMatch(reservation -> reservation.getTable().getId() == tableId &&
                            reservation.getDate().equals(date) &&
                            isOverlapping(reservation.getArrivalTime(), reservation.getDepartureTime(), arrivalTime, departureTime));

            if (isAlreadyBooked) {
                return "Sorry! We're booked!";
            }

            // Create new reservation and add to in-memory list
            Reservation newReservation = new Reservation(customerName, date, arrivalTime, departureTime, table.get());
            reservations.add(newReservation);

            // Save reservation to MongoDB
            ReservationDocument reservationDocument = new ReservationDocument();
            reservationDocument.setCustomerName(customerName);
            reservationDocument.setDate(date);
            reservationDocument.setArrivalTime(arrivalTime);
            reservationDocument.setDepartureTime(departureTime);
            reservationDocument.setTableId(table.get().getId());
            reservationDocument.setTableSize(table.get().getSize()); // Ensure this is set

            // Save to MongoDB
            reservationRepository.save(reservationDocument);


            return "Table reserved successfully from " + arrivalTime + " to " + departureTime + ".";
        }

        return "Error: Table not found.";
    }
    
    public List<Reservation> getReservations() {
        return reservations;
    }

    public List<Table> getTables() {
        return tables;
    }

    private boolean isOverlapping(String existingArrival, String existingDeparture, String newArrival, String newDeparture) {
        return !(newDeparture.compareTo(existingArrival) <= 0 || newArrival.compareTo(existingDeparture) >= 0);
    }

    // Delete a reservation with table ID included
    public String deleteReservation(String customerName, String date, String arrivalTime, int tableId) {
        Reservation toRemove = null;

        for (Reservation reservation : reservations) {
            if (reservation.getCustomerName().equalsIgnoreCase(customerName) &&
                reservation.getDate().equals(date) &&
                reservation.getArrivalTime().equals(arrivalTime) &&
                reservation.getTable().getId() == tableId) {
                toRemove = reservation;
                break;
            }
        }

        if (toRemove != null) {
            reservations.remove(toRemove);

            // Also delete from MongoDB
            List<ReservationDocument> docs = reservationRepository
                    .findByCustomerNameAndDate(customerName, date);

            for (ReservationDocument doc : docs) {
                if (doc.getArrivalTime().equals(arrivalTime) && doc.getTableId() == tableId) {
                    reservationRepository.deleteById(doc.getId());
                    break;
                }
            }

            return "Reservation cancelled successfully.";
        }

        return "Reservation not found.";
    }

    // Search reservations by customer name
    public List<Reservation> searchReservationsByName(String name) {
        List<Reservation> filteredReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomerName().toLowerCase().contains(name.toLowerCase())) {
                filteredReservations.add(reservation);
            }
        }
        return filteredReservations;
    }
}
