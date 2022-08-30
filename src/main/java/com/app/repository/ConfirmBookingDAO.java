package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Confirm_Booking;

@Repository
public interface ConfirmBookingDAO extends JpaRepository<Confirm_Booking, Integer> {
	
	public Confirm_Booking findByBookingId(String bookingId);
	
}
