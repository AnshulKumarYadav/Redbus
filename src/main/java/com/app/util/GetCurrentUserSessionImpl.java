package com.app.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.exception.UserException;
import com.app.model.CurrentUserSession;
import com.app.model.User;
import com.app.repository.UserDAO;
import com.app.repository.UserSessionDAO;


@Component
public class GetCurrentUserSessionImpl implements GetCurrentUserSession {
	
	@Autowired
	private UserSessionDAO userSessionDAO;
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public CurrentUserSession getCurrentUserSession(String key) {
		Optional<CurrentUserSession> userOptional = userSessionDAO.findByUuid(key);
		if(userOptional.isPresent())
		{
			return userOptional.get();
		}
		else {
			throw new UserException("The user is not authorized");
		}
	}

	@Override
	public Integer getCurrentUserSessionAdminId(String key) {
		Optional<CurrentUserSession> userOptional = userSessionDAO.findByUuid(key);
		if(userOptional.isPresent())
		{
			return userOptional.get().getUserId();
		}
		else {
			throw new UserException("The user is not authorized");
		}
	}

	@Override
	public User getCurrentUser(String key) {
		Optional<CurrentUserSession> userOptional = userSessionDAO.findByUuid(key);
		if(userOptional.isPresent())
		{
			Integer id = userOptional.get().getUserId();
			return userDAO.getById(id);
		}
		else {
			throw new UserException("The user is not authorized");
		}
	}
	
	

}
