package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Bus;

@Repository
public interface BusDAO extends JpaRepository<Bus, Integer> {
	
	public Bus findByBusNumber(String busNumber);
	
	public List<Bus> findBySourceStationAndDestinationStation(String sourceStation,String destinationStation);
	

}
