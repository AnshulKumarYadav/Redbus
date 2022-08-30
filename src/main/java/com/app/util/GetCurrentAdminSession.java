package com.app.util;

import com.app.model.Admin;
import com.app.model.CurrentAdminSession;

public interface GetCurrentAdminSession {
	
    public CurrentAdminSession getCurrentAdminSession(String key);
	
	public Integer getCurrentAdminSessionAdminId(String key);
	
	public Admin getCurrentAdmin(String key);

}
