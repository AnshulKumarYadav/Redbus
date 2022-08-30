package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Cancelled_Booking;

@Repository
public interface CancelledBookingDAO extends JpaRepository<Cancelled_Booking, Integer> {

}
