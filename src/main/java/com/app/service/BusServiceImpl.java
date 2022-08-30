package com.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.DTO.CancelBookDTO;
import com.app.DTO.ConfirmBookingDTO;
import com.app.exception.AdminException;
import com.app.exception.UserException;
import com.app.model.Admin;
import com.app.model.Book;
import com.app.model.Bus;
import com.app.model.Cancelled_Booking;
import com.app.model.Confirm_Booking;
import com.app.model.User;
import com.app.repository.BookDAO;
import com.app.repository.BusDAO;
import com.app.repository.CancelledBookingDAO;
import com.app.repository.ConfirmBookingDAO;
import com.app.util.GetCurrentAdminSession;
import com.app.util.GetCurrentUserSession;

import net.bytebuddy.utility.RandomString;

@Service
public class BusServiceImpl implements BusService {
	
	@Autowired
	private BusDAO busDAO;
	
	@Autowired
	private GetCurrentAdminSession getCurrentAdminSession;
	
	@Autowired
	private GetCurrentUserSession getCurrentUserSession;
	
	@Autowired
	private BookDAO bookDAO;
	
	@Autowired
	private ConfirmBookingDAO confirmBookingDAO;
	
	@Autowired
	private CancelledBookingDAO cancelledBookingDAO;

	@Override
	public Bus addBus(Bus bus, String key) {
		Admin admin = getCurrentAdminSession.getCurrentAdmin(key);
		if(admin!=null)
		{
			Bus existingBus = busDAO.findByBusNumber(bus.getBusNumber());
			if(existingBus==null)
			{
				return busDAO.save(bus);
			}
			else {
				throw new UserException("Bus is already exists");
			}
		}
		else {
			throw new AdminException("Admin is not logged in, please try to log in");
		}
	}

	@Override
	public Bus updateBus(Bus bus, String key) {
		Admin admin = getCurrentAdminSession.getCurrentAdmin(key);
		if(admin!=null)
		{
			Bus existingBus = busDAO.findByBusNumber(bus.getBusNumber());
			if(existingBus==null)
			{
				throw new UserException("Bus does not exists");
			}
			else {
				existingBus.setSourceStation(bus.getSourceStation());
				existingBus.setDestinationStation(bus.getDestinationStation());
				existingBus.setAvailable_seats(bus.getAvailable_seats());
				return busDAO.save(existingBus);
			}
		}
		else {
			throw new AdminException("Admin is not logged in, please try to log in");
		}
	}

	@Override
	public Bus removeBus(String busNumber, String key) {
		Admin admin = getCurrentAdminSession.getCurrentAdmin(key);
		if(admin!=null)
		{
			Bus existingBus = busDAO.findByBusNumber(busNumber);
			if(existingBus!=null)
			{
				busDAO.delete(existingBus);
				return existingBus;
			}
			else {
				throw new UserException("Bus does not exist");
			}
		}
		else {
			throw new AdminException("Admin is not logged in, please try to log in");
		}
	}

	@Override
	public List<Bus> getAllBus() {
		List<Bus> busList = busDAO.findAll();
		if(busList.isEmpty())
		{
			throw new UserException("There is no bus in the database");
		}
		else {
			return busList;
		}
	}

	@Override
	public List<Bus> getAllBusBySD(String source, String destination) {
		List<Bus> busList = busDAO.findBySourceStationAndDestinationStation(source, destination);
		if(busList.isEmpty())
		{
			throw new UserException("There is no bus available for this source and destination");
		}
		else {
			return busList;
		}
	}

	@Override
	public Book bookBus(Book book, String userkey) {
		
		User user = getCurrentUserSession.getCurrentUser(userkey);
		
		if(user!=null)
		{
			Bus bus = busDAO.findByBusNumber(book.getBusNumber());
			if(bus!=null)
			{
				return bookDAO.save(book);
			}
			else {
				throw new UserException("The bus doesn't exists");
			}
		}
		else {
			throw new UserException("User is not logged in");
		}
	
	}

	@Override
	public ConfirmBookingDTO confirmTicketBooking(String userkey,Integer bookId) {
		
		User user = getCurrentUserSession.getCurrentUser(userkey);
		if(user!=null)
		{
			Optional<Book> book = bookDAO.findById(bookId);
			if(book.isPresent())
			{
				
				
				String busNumber = book.get().getBusNumber();
				Bus bus = busDAO.findByBusNumber(busNumber);
				if(bus!=null)
				{
			       if(bus.getAvailable_seats()>= book.get().getNoOfSeats())
			       {
			    	   String bookingId = RandomString.make(10);
						Integer totalFare = bus.getFare_per_seat() * book.get().getNoOfSeats(); 
						Confirm_Booking confirm_Booking = new Confirm_Booking(bookingId, book.get().getNoOfSeats(), totalFare);
						confirmBookingDAO.save(confirm_Booking);
						bus.setAvailable_seats(bus.getAvailable_seats()-book.get().getNoOfSeats());
						busDAO.save(bus);
						return new ConfirmBookingDTO(LocalDateTime.now(), book.get().getSourceStation(), book.get().getDestinationStation(), bookingId,busNumber);
			       }
			       else {
					   throw new UserException("Seats are not availavle for this bus");
				   }
				}
				else {
					throw new UserException("Bus is not available with this number");
				}
			}
			throw new UserException("Booking is not found");
		}
		else {
			throw new UserException("User is not logged in");
		}
		
		
	}
	
	public String cancelBooking(String userkey,CancelBookDTO cancelBookDTO,Integer bookId)
	{
		User user = getCurrentUserSession.getCurrentUser(userkey);
		if(user!=null)
		{
			Confirm_Booking confirmed_Booking = confirmBookingDAO.findByBookingId(cancelBookDTO.getBookingNumber());
			if(confirmed_Booking!=null)
			{
				Bus bus = busDAO.findByBusNumber(cancelBookDTO.getBusNumber());
				if(bus!=null)
				{
					Optional<Book> book = bookDAO.findById(bookId);
					if(book.isPresent())
					{
						Cancelled_Booking cancelled_Booking = new Cancelled_Booking(cancelBookDTO.getBookingNumber(), confirmed_Booking.getTotalSeats(), confirmed_Booking.getTotalFare());
						cancelledBookingDAO.save(cancelled_Booking);
						confirmBookingDAO.save(confirmed_Booking);
						bus.setAvailable_seats(bus.getAvailable_seats()+confirmed_Booking.getTotalSeats());
						busDAO.save(bus);
						bookDAO.save(book.get());
						return "Booking canceled....";
					}
					else {
						throw new UserException("No booking found with this bookingId");
					}
					
				}
				else {
					throw new UserException("No bus available with this bus number");
				}
			}
			else {
				throw new UserException("No booking found with this bookingNumber");
			}
		}
		else {
			throw new UserException("User is not logged in");
		}
	}
	
	public Book getBookingDetails(String userKey,Integer bookId)
	{
		User user = getCurrentUserSession.getCurrentUser(userKey);
		if(user!=null)
		{
		 Optional<Book> bOptional = bookDAO.findById(bookId);
		 if(bOptional.isPresent())
		 {
			return bOptional.get();
		 }
		 else {
			throw new UserException("Booking details not found, please give correct book id");
		 }
		}
		else {
			throw new UserException("User is not logged in..");
		}
	}

}
