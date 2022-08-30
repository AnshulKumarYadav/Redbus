package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Passenger;

public interface PassengerDAO extends JpaRepository<Passenger, Integer> {

}
