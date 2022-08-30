package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.CurrentUserSession;

@Repository
public interface UserSessionDAO extends JpaRepository<CurrentUserSession, Integer> {
	
    public Optional<CurrentUserSession>  findByUserId(Integer userId);
	
	public Optional<CurrentUserSession>  findByUuid(String uuid);

}
