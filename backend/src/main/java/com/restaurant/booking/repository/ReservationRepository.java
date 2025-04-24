package com.restaurant.booking.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.booking.model.ReservationDocument;
// Adapter pattern: Adapt external data model (MongoDocument) into internal application model (Reservation)
@Repository
public interface ReservationRepository extends MongoRepository<ReservationDocument, String> {
    List<ReservationDocument> findByTableIdAndDate(int tableId, String date);
    List<ReservationDocument> findByCustomerNameAndDate(String customerName, String date);
}
