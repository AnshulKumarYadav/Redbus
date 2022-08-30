package com.app.util;

import com.app.model.CurrentUserSession;
import com.app.model.User;

public interface GetCurrentUserSession {
	
   public CurrentUserSession getCurrentUserSession(String key);
	
	public Integer getCurrentUserSessionAdminId(String key);
	
	public User getCurrentUser(String key);

}
