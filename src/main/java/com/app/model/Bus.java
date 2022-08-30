package com.app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer busId;
	
	@NotNull
	private String sourceStation;
	
	@NotNull
	private String destinationStation;
	
	@NotNull
//	@Pattern(regexp="[A-Z0-9]{1}[0-9]{15}", message = "Bus number must be in proper format.")
	private String busNumber;
	
	@NotNull
	private Integer available_seats;
	
	@NotNull
	private Integer fare_per_seat;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Bus_Timing> bus_Timing;
	

}
