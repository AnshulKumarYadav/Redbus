package com.app.service;

import java.util.List;

import com.app.DTO.CancelBookDTO;
import com.app.DTO.ConfirmBookingDTO;
import com.app.model.Book;
import com.app.model.Bus;



public interface BusService {
	
    public Bus addBus(Bus bus, String key);

	public Bus updateBus(Bus bus, String key);
	
	public Bus removeBus(String busNumber, String key);
	
	public List<Bus> getAllBus();
	
	public List<Bus> getAllBusBySD(String source,String destination);
	
	public Book bookBus(Book book,String userkey);
	
	public ConfirmBookingDTO confirmTicketBooking(String userkey,Integer bookId);
	
	public String cancelBooking(String userkey,CancelBookDTO cancelBookDTO,Integer bookId);
	
	public Book getBookingDetails(String userKey,Integer bookId);

}
