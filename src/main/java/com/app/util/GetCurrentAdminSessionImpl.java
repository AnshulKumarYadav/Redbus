package com.app.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.exception.AdminException;
import com.app.model.Admin;
import com.app.model.CurrentAdminSession;
import com.app.repository.AdminDAO;
import com.app.repository.SessionDAO;

@Component
public class GetCurrentAdminSessionImpl implements GetCurrentAdminSession {

	@Autowired
	private SessionDAO sessionDAO;

	@Autowired
	private AdminDAO adminDAO;
	
	@Override
	public Integer getCurrentAdminSessionAdminId(String key){
		Optional<CurrentAdminSession> optional = sessionDAO.findByUuid(key);
		
		if(!optional.isPresent()) {
			throw new AdminException("The user is not authorised as Admin");
		}
		
		return optional.get().getAdminId();
	}
	
	@Override
	public Admin getCurrentAdmin(String key) {
		Optional<CurrentAdminSession> optional = sessionDAO.findByUuid(key);
		
		if(!optional.isPresent()) {
			throw new AdminException("The user is not authorised as Admin");
		}
		
		Integer adminId = optional.get().getAdminId();
		
		return  adminDAO.getById(adminId);
	}

	@Override
	public CurrentAdminSession getCurrentAdminSession(String key) {
		Optional<CurrentAdminSession> optional = sessionDAO.findByUuid(key);

		if (!optional.isPresent()) {
			throw new AdminException("The user is not authorised as Admin");
		}

		return optional.get();
	}

	
	

}
