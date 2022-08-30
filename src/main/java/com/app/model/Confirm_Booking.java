package com.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Confirm_Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String bookingId;
	private Integer totalSeats;
	private Integer totalFare;
	
	

	public Confirm_Booking(String bookingId, Integer totalSeats, Integer totalFare) {
		super();
		this.bookingId = bookingId;
		this.totalSeats = totalSeats;
		this.totalFare = totalFare;
		
	}
	
	

	
	
	
	

}
