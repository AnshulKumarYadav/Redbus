package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Address;

@Repository
public interface AddressDAO extends JpaRepository<Address, Integer> {

}
