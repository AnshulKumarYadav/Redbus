package com.app.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.DTO.LoginDTO;
import com.app.exception.AdminException;
import com.app.exception.UserException;
import com.app.model.CurrentUserSession;
import com.app.model.User;
import com.app.repository.UserDAO;
import com.app.repository.UserSessionDAO;
import com.app.util.GetCurrentUserSession;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserSessionDAO userSessionDAO;
	
	@Autowired
	private GetCurrentUserSession getCurrentUserSession;

	@Override
	public User registerUser(User user) {
		User existingUser = userDAO.findByMobile(user.getMobile());
		if(existingUser==null)
		{
			return userDAO.save(user);
		}
		throw new UserException("User already exists");
	}

	@Override
	public CurrentUserSession logIntoAccount(LoginDTO userDTO) {
		User existingUser = userDAO.findByMobile(userDTO.getMobile());
		if(existingUser!=null)
		{
			Optional<CurrentUserSession> userOptional = userSessionDAO.findByUserId(existingUser.getUserId());
			if(userOptional.isPresent())
			{
				throw new UserException("User already logged in");
			}
			if(existingUser.getPassword().equals(userDTO.getPassword()))
			{
				String key = RandomString.make(8);
				CurrentUserSession currentUserSession = new CurrentUserSession(existingUser.getUserId(), key, LocalDateTime.now());
				userSessionDAO.save(currentUserSession);
				return currentUserSession;
			}
			else {
				throw new UserException("Invalid mobile or password");
			}
		}
		else {
			throw new UserException("User does not exists");
		}
	}

	@Override
	public User getUser(String key) {
		User user = getCurrentUserSession.getCurrentUser(key);
		if(user!=null)
		{
			return new User(user.getUserId(), user.getUserName(), user.getEmail(), user.getMobile(), user.getPassword());
		}
		else {
			throw new UserException("User does not exist with this key");
		}
	}

	@Override
	public User updateUser(User user, String key) {
		User curruser = getCurrentUserSession.getCurrentUser(key);
		if(curruser!=null)
		{
			User existingUser = userDAO.findByMobile(curruser.getMobile());
			if(existingUser!=null)
			{
				existingUser.setUserName(user.getUserName());
				existingUser.setEmail(user.getEmail());
				existingUser.setPassword(user.getPassword());
				return userDAO.save(existingUser);
			}
			else {
				throw new UserException("User does not exists with this mobile");
			}
		}
		else {
			throw new UserException("User does not exist with this key");
		}
	}

	@Override
	public User deleteUser(String key) {
		User curruser = getCurrentUserSession.getCurrentUser(key);
		if(curruser!=null)
		{
			userDAO.delete(curruser);
			return curruser;
		}
		else {
			throw new UserException("User does not exist with this key");
		}
	}

	@Override
	public String logOutFromAccount(String key) {
        Optional<CurrentUserSession> currentUserOptional = userSessionDAO.findByUuid(key);
		
		if(!currentUserOptional.isPresent()) {
			throw new AdminException("User is not logged in with this number");
		}
		
		CurrentUserSession currentUserSession = getCurrentUserSession.getCurrentUserSession(key);
		userSessionDAO.delete(currentUserSession);
		
		return "Logged Out...";
	}

}
