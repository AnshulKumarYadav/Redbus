package com.app.service;
import com.app.DTO.LoginDTO;
import com.app.model.CurrentUserSession;
import com.app.model.User;

public interface UserService {
	
    public User registerUser(User user);
	
	public CurrentUserSession logIntoAccount(LoginDTO userDTO);
	
	public User getUser(String key);
	
	public User updateUser(User user, String key);
	
	public User deleteUser(String key);
	
	public String logOutFromAccount(String key);

}
