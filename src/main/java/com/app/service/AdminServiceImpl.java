package com.app.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.DTO.LoginDTO;
import com.app.exception.AdminException;
import com.app.model.Admin;
import com.app.model.CurrentAdminSession;
import com.app.repository.AdminDAO;
import com.app.repository.SessionDAO;
import com.app.util.GetCurrentAdminSessionImpl;

import net.bytebuddy.utility.RandomString;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private GetCurrentAdminSessionImpl getCurrentAdminSession;

	@Override
	public Admin registerAdmin(Admin admin) {
		Admin existingAdmin = adminDAO.findByMobile(admin.getMobile());
		if(existingAdmin==null)
		{
			return adminDAO.save(admin);
		}
		throw new AdminException("Admin already exists please try to login");
	}

	@Override
	public CurrentAdminSession logIntoAccount(LoginDTO adminDTO) {
		Admin existingAdmin = adminDAO.findByMobile(adminDTO.getMobile());
		if(existingAdmin!=null) {
			Optional<CurrentAdminSession> currentAdmin = sessionDAO.findByAdminId(existingAdmin.getAdminId());
			if(currentAdmin.isPresent())
			{
				throw new AdminException("Admin already logged in");
			}
			if(existingAdmin.getPassword().equals(adminDTO.getPassword()))
			{
				String key = RandomString.make(6);
				
				CurrentAdminSession currentAdminSession = new CurrentAdminSession(existingAdmin.getAdminId(), key, LocalDateTime.now());
				
				sessionDAO.save(currentAdminSession);
				
				return currentAdminSession;
			}
		}
			throw new AdminException("Admin does not exist with this given mobile");
		
	}

	@Override
	public Admin getAdmin(String key) {
		Admin admin = getCurrentAdminSession.getCurrentAdmin(key);
		if(admin!=null)
		{
			return admin;
		}
		throw new AdminException("Invalid Uuid");
	}

	@Override
	public Admin updateAdmin(Admin admin,String key) {
		
		Admin currentAdmin = getCurrentAdminSession.getCurrentAdmin(key);
		if(currentAdmin!=null)
		{
			Admin existingAdmin = adminDAO.findByMobile(admin.getMobile());
			if(existingAdmin==null)
			{
				throw new AdminException("Admin does not exist with the mobile");
			}
			existingAdmin.setAdminName(admin.getAdminName());
			existingAdmin.setEmail(admin.getEmail());
			existingAdmin.setPassword(admin.getPassword());
			return existingAdmin;
		}
		throw new AdminException("Invalid Uuid");
		
	}

	@Override
	public Admin deleteAdmin(String key) {
		Admin currentAdmin = getCurrentAdminSession.getCurrentAdmin(key);
		if(currentAdmin!=null)
		{
			adminDAO.delete(currentAdmin);
			return currentAdmin;
		}
		else {
			throw new AdminException("Invalid Uuid");
		}
		
	}

	@Override
	public String logOutFromAccount(String key) {
        Optional<CurrentAdminSession> currentUserOptional = sessionDAO.findByUuid(key);
		
		if(!currentUserOptional.isPresent()) {
			throw new AdminException("User is not logged in with this number");
		}
		
		CurrentAdminSession currentAdminSession = getCurrentAdminSession.getCurrentAdminSession(key);
		sessionDAO.delete(currentAdminSession);
		
		return "Logged Out...";
	}

}
