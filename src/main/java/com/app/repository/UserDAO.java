package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
	
	public User findByMobile(String mobile);

}
