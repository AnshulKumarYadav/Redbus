package com.app.service;

import com.app.DTO.LoginDTO;
import com.app.model.Admin;
import com.app.model.CurrentAdminSession;

public interface AdminService {
	
	public Admin registerAdmin(Admin admin);
	
	public CurrentAdminSession logIntoAccount(LoginDTO adminDTO);
	
	public Admin getAdmin(String key);
	
	public Admin updateAdmin(Admin admin, String key);
	
	public Admin deleteAdmin(String key);
	
	public String logOutFromAccount(String key);
	
	
	
}
