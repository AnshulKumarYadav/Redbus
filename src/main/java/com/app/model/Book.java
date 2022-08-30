package com.app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer bookid;
	
	@NotNull
	private Integer noOfSeats;
	
	@NotNull
	private String sourceStation;
	
	@NotNull
	private String destinationStation;
	
	@NotNull
//	@Pattern(regexp="[A-Z0-9]{1}[0-9]{15}", message = "Bus number must be in proper format.")
	private String busNumber;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Bus_Timing bus_Timing;
	
//	@OneToOne(cascade = CascadeType.ALL)
//	private Confirm_Booking confirm_Booking;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Passenger> passenger;
	
	@OneToOne(cascade = CascadeType.ALL)
    private Address address;

}
