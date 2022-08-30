package com.app.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmBookingDTO {
	
	private LocalDateTime journeyDateTime;
	private String journeyFrom;
	private String journeyTo;
	private String bookingNumber;
	private String busNumber;
	
	
	
	
	
}
