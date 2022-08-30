package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.CurrentAdminSession;

@Repository
public interface SessionDAO extends JpaRepository<CurrentAdminSession, Integer> {
	
    public Optional<CurrentAdminSession>  findByAdminId(Integer adminId);
	
	public Optional<CurrentAdminSession>  findByUuid(String uuid);

}
